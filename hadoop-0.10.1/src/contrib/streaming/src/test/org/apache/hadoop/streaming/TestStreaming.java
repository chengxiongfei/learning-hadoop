/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.streaming;

import junit.framework.TestCase;
import java.io.*;
import java.util.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * This class tests hadoopStreaming in MapReduce local mode.
 */
public class TestStreaming extends TestCase
{

  // "map" command: grep -E (red|green|blue)
  // reduce command: uniq
  protected File INPUT_FILE = new File("input.txt");
  protected File OUTPUT_DIR = new File("out");
  protected String input = "roses.are.red\nviolets.are.blue\nbunnies.are.pink\n";
  // map behaves like "/usr/bin/tr . \\n"; (split words into lines)
  protected String map = StreamUtil.makeJavaCommand(TrApp.class, new String[]{".", "\\n"});
  // combine, reduce behave like /usr/bin/uniq. But also prepend lines with C, R.
  protected String combine  = StreamUtil.makeJavaCommand(UniqApp.class, new String[]{"C"});
  protected String reduce = StreamUtil.makeJavaCommand(UniqApp.class, new String[]{"R"});
  protected String outputExpect = "RCare\t\nRCblue\t\nRCbunnies\t\nRCpink\t\nRCred\t\nRCroses\t\nRCviolets\t\n";

  private StreamJob job;

  public TestStreaming() throws IOException
  {
    UtilTest utilTest = new UtilTest(getClass().getName());
    utilTest.checkUserDir();
    utilTest.redirectIfAntJunit();
  }

  protected void createInput() throws IOException
  {
    DataOutputStream out = new DataOutputStream(
        new FileOutputStream(INPUT_FILE.getAbsoluteFile()));
    out.write(input.getBytes("UTF-8"));
    out.close();
  }

  protected String[] genArgs() {
    return new String[] {
        "-input", INPUT_FILE.getAbsolutePath(),
        "-output", OUTPUT_DIR.getAbsolutePath(),
        "-mapper", map,
        "-combiner", combine,
        "-reducer", reduce,
        //"-verbose",
        //"-jobconf", "stream.debug=set"
        "-jobconf", "keep.failed.task.files=true"
        };
  }
  
  public void testCommandLine()
  {
    try {
      createInput();
      boolean mayExit = false;

      // During tests, the default Configuration will use a local mapred
      // So don't specify -config or -cluster
      job = new StreamJob(genArgs(), mayExit);      
      job.go();
      File outFile = new File(OUTPUT_DIR, "part-00000").getAbsoluteFile();
      String output = StreamUtil.slurp(outFile);
      outFile.delete();
      System.err.println("outEx1=" + outputExpect);
      System.err.println("  out1=" + output);
      assertEquals(outputExpect, output);
    } catch(Exception e) {
      failTrace(e);
    } finally {
      INPUT_FILE.delete();
      OUTPUT_DIR.delete();
    }
  }

  private void failTrace(Exception e)
  {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    fail(sw.toString());
  }

  public static void main(String[]args) throws Exception
  {
    new TestStreaming().testCommandLine();
  }

}
