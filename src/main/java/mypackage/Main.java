package mypackage;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class Main {

  public static void main( String... args ) {
    printOptionsAndArguments( args );
    System.out.println( "\nCTRL+C to quit..." );

    Thread runtimeHookThread = new Thread() {
      public void run() {
        shutdown();
      }
    };

    Runtime.getRuntime().addShutdownHook( runtimeHookThread );

    try {
      while (true) {
        Thread.sleep ( 60000 );
        System.out.println( "running" );
      }
    }
    catch (Throwable t) {
      System.out.println( "Exception: " + t.toString() );
    }
  }

  private static void shutdown() {
    System.out.println( "Shutdown hook completed." );
  }

  private static void printOptionsAndArguments( String... args ) {
    RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
    List<String> arguments = runtimeMxBean.getInputArguments();

    System.out.println( "JVM Options:" );
    if ( arguments.size() == 0 ) {
      System.out.println( "  <none>" );
    }
    for ( String argument: arguments ) {
      System.out.println( "  " + argument );
    }

    System.out.println( "App arguments:" );
    if ( args == null || args.length == 0 ) {
      System.out.println( "  <none>" );
    }
    for ( int i=0; i<args.length; i++ ) {
      System.out.println( "  " + args[i] );
    }
  }

}
