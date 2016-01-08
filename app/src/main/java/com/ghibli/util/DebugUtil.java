package com.ghibli.util;

public class DebugUtil {

  public static boolean DEBUG = true;

  public static void log(String message) {
    if(DebugUtil.DEBUG) {
      System.out.println("LOG: " + message);
    }
  }

}
