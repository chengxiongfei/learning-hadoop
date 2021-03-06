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

package org.apache.hadoop.record.compiler;

/**
 *
 * @author Milind Bhandarkar
 */
public class JLong extends JType {
    
    /** Creates a new instance of JLong */
    public JLong() {
        super("int64_t", "long", "Long", "Long", "toLong");
    }
    
    public String getSignature() {
        return "l";
    }
    
    public String genJavaHashCode(String fname) {
        return "    ret = (int) ("+fname+"^("+fname+">>>32));\n";
    }
    
    public String genJavaSlurpBytes(String b, String s, String l) {
      StringBuffer sb = new StringBuffer();
      sb.append("        {\n");
      sb.append("           long i = WritableComparator.readVLong("+b+", "+s+");\n");
      sb.append("           int z = WritableUtils.getVIntSize(i);\n");
      sb.append("           "+s+"+=z; "+l+"-=z;\n");
      sb.append("        }\n");
      return sb.toString();
    }
    
    public String genJavaCompareBytes() {
      StringBuffer sb = new StringBuffer();
      sb.append("        {\n");
      sb.append("           long i1 = WritableComparator.readVLong(b1, s1);\n");
      sb.append("           long i2 = WritableComparator.readVLong(b2, s2);\n");
      sb.append("           if (i1 != i2) {\n");
      sb.append("             return ((i1-i2) < 0) ? -1 : 0;\n");
      sb.append("           }\n");
      sb.append("           int z1 = WritableUtils.getVIntSize(i1);\n");
      sb.append("           int z2 = WritableUtils.getVIntSize(i2);\n");
      sb.append("           s1+=z1; s2+=z2; l1-=z1; l2-=z2;\n");
      sb.append("        }\n");
      return sb.toString();
    }
}
