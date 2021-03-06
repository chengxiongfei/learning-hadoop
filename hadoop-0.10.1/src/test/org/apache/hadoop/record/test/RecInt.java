// File generated by hadoop record compiler. Do not edit.
package org.apache.hadoop.record.test;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.io.Text;

public class RecInt implements org.apache.hadoop.record.Record, WritableComparable {
  private static final Log LOG= LogFactory.getLog("org.apache.hadoop.record.test.RecInt");
  private int mData;
  private java.util.BitSet bs_;
  public RecInt() {
    bs_ = new java.util.BitSet(2);
    bs_.set(1);
  }
  public RecInt(
        int m0) {
    bs_ = new java.util.BitSet(2);
    bs_.set(1);
    mData=m0; bs_.set(0);
  }
  public int getData() {
    return mData;
  }
  public void setData(int m_) {
    mData=m_; bs_.set(0);
  }
  public void serialize(org.apache.hadoop.record.OutputArchive a_, String tag) throws java.io.IOException {
    if (!validate()) throw new java.io.IOException("All fields not set:");
    a_.startRecord(this,tag);
    a_.writeInt(mData,"Data");
    bs_.clear(0);
    a_.endRecord(this,tag);
  }
  public void deserialize(org.apache.hadoop.record.InputArchive a_, String tag) throws java.io.IOException {
    a_.startRecord(tag);
    mData=a_.readInt("Data");
    bs_.set(0);
    a_.endRecord(tag);
}
  public String toString() {
    try {
      java.io.ByteArrayOutputStream s =
        new java.io.ByteArrayOutputStream();
      org.apache.hadoop.record.CsvOutputArchive a_ = 
        new org.apache.hadoop.record.CsvOutputArchive(s);
      a_.startRecord(this,"");
    a_.writeInt(mData,"Data");
      a_.endRecord(this,"");
      return new String(s.toByteArray(), "UTF-8");
    } catch (Throwable ex) {
      ex.printStackTrace();
    }
    return "ERROR";
  }
  public void write(java.io.DataOutput out) throws java.io.IOException {
    org.apache.hadoop.record.BinaryOutputArchive archive = new org.apache.hadoop.record.BinaryOutputArchive(out);
    serialize(archive, "");
  }
  public void readFields(java.io.DataInput in) throws java.io.IOException {
    org.apache.hadoop.record.BinaryInputArchive archive = new org.apache.hadoop.record.BinaryInputArchive(in);
    deserialize(archive, "");
  }
  public boolean validate() {
    if (bs_.cardinality() != bs_.length()) return false;
    return true;
}
  public int compareTo (Object peer_) throws ClassCastException {
    if (!(peer_ instanceof RecInt)) {
      throw new ClassCastException("Comparing different types of records.");
    }
    RecInt peer = (RecInt) peer_;
    int ret = 0;
    ret = (mData == peer.mData)? 0 :((mData<peer.mData)?-1:1);
    if (ret != 0) return ret;
     return ret;
  }
  public boolean equals(Object peer_) {
    if (!(peer_ instanceof RecInt)) {
      return false;
    }
    if (peer_ == this) {
      return true;
    }
    RecInt peer = (RecInt) peer_;
    boolean ret = false;
    ret = (mData==peer.mData);
    if (!ret) return ret;
     return ret;
  }
  public int hashCode() {
    int result = 17;
    int ret;
    ret = (int)mData;
    result = 37*result + ret;
    return result;
  }
  public static String signature() {
    return "LRecInt(i)";
  }
  public static class Comparator extends WritableComparator {
    public Comparator() {
      super(RecInt.class);
    }
    static public int slurpRaw(byte[] b, int s, int l) {
      try {
        int os = s;
        {
           int i = WritableComparator.readVInt(b, s);
           int z = WritableUtils.getVIntSize(i);
           s+=z; l-=z;
        }
        return (os - s);
      } catch(IOException e) {
        LOG.warn(e);
        throw new RuntimeException(e);
      }
    }
    static public int compareRaw(byte[] b1, int s1, int l1,
                       byte[] b2, int s2, int l2) {
      try {
        int os1 = s1;
        {
           int i1 = WritableComparator.readVInt(b1, s1);
           int i2 = WritableComparator.readVInt(b2, s2);
           if (i1 != i2) {
             return ((i1-i2) < 0) ? -1 : 0;
           }
           int z1 = WritableUtils.getVIntSize(i1);
           int z2 = WritableUtils.getVIntSize(i2);
           s1+=z1; s2+=z2; l1-=z1; l2-=z2;
        }
        return (os1 - s1);
      } catch(IOException e) {
        LOG.warn(e);
        throw new RuntimeException(e);
      }
    }
    public int compare(byte[] b1, int s1, int l1,
                       byte[] b2, int s2, int l2) {
      int ret = compareRaw(b1,s1,l1,b2,s2,l2);
      return (ret == -1)? -1 : ((ret==0)? 1 : 0);    }
  }

  static {
    WritableComparator.define(RecInt.class, new Comparator());
  }
}
