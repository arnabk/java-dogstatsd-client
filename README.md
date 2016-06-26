##  **java-dogstatsd-client** ![Build Status](https://travis-ci.org/arnabk/java-dogstatsd-client.svg?branch=master)

A statsd client library implemented in Java.  Allows for Java applications to easily communicate with statsd.

This version is forked from the upstream [java-statsd-client](https://github.com/indeedeng/java-dogstatsd-client) project, adding support for [DataDog](http://datadoghq.com/) events and blocking metrics for use with [dogstatsd](http://docs.datadoghq.com/guides/dogstatsd/).

Downloads
---------
The client jar is distributed via maven central

```xml
<dependency>
    <groupId>com.github.arnabk</groupId>
    <artifactId>java-dogstatsd-client</artifactId>
    <version>1.0.3</version>
</dependency>
```

Usage
-----

**Non-blocking usage (metrics)**
```java
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

public class Foo {

  private static final StatsDClient statsd = new NonBlockingStatsDClient(
    "my.prefix",                          /* prefix to any stats; may be null or empty string */
    "statsd-host",                        /* common case: localhost */
    8125,                                 /* port */
    new String[] {"tag:value"}            /* DataDog extension: Constant tags, always applied */
  );

  public static final void main(String[] args) {
    statsd.incrementCounter("foo");
    statsd.recordGaugeValue("bar", 100);
    statsd.recordGaugeValue("baz", 0.01); /* DataDog extension: support for floating-point gauges */
    statsd.recordHistogramValue("qux", 15);     /* DataDog extension: histograms */
    statsd.recordHistogramValue("qux", 15.5);   /* ...also floating-point */

    /* Compatibility note: Unlike upstream statsd, DataDog expects execution times to be a
     * floating-point value in seconds, not a millisecond value. This library
     * does the conversion from ms to fractional seconds.
     */
    statsd.recordExecutionTime("bag", 25, "cluster:foo"); /* DataDog extension: cluster tag */
  }
}
```

**Blocking usage (metrics)**
```java
import com.github.arnabk.statsd.BlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

public class Foo {

  private static final StatsDClient statsd = new BlockingStatsDClient(
    "my.prefix",                          /* prefix to any stats; may be null or empty string */
    "statsd-host",                        /* common case: localhost */
    8125,                                 /* port */
    new String[] {"tag:value"}            /* DataDog extension: Constant tags, always applied */
  );

  public static final void main(String[] args) {
    statsd.incrementCounter("foo");
    statsd.recordGaugeValue("bar", 100);
    statsd.recordGaugeValue("baz", 0.01); /* DataDog extension: support for floating-point gauges */
    statsd.recordHistogramValue("qux", 15);     /* DataDog extension: histograms */
    statsd.recordHistogramValue("qux", 15.5);   /* ...also floating-point */

    /* Compatibility note: Unlike upstream statsd, DataDog expects execution times to be a
     * floating-point value in seconds, not a millisecond value. This library
     * does the conversion from ms to fractional seconds.
     */
    statsd.recordExecutionTime("bag", 25, "cluster:foo"); /* DataDog extension: cluster tag */
  }
}
```

**Non-blocking usage (events)**
```java
import com.github.arnabk.statsd.NonBlockingStatsDEventClient;

public class Foo {

  private static final NonBlockingStatsDEventClient statsd = new NonBlockingStatsDEventClient(
    "statsd-host",                        /* common case: localhost */
    8125,                                 /* port */
    new String[] {"tag:value"}            /* DataDog extension: Constant tags, always applied */
  );

  public static final void main(String[] args) {
    statsd.event("title", "message");
  }
}
```


**Blocking usage (events)**
```java
import com.github.arnabk.statsd.BlockingStatsDEventClient;

public class Foo {

  private static final BlockingStatsDEventClient statsd = new BlockingStatsDEventClient(
    "statsd-host",                        /* common case: localhost */
    8125,                                 /* port */
    new String[] {"tag:value"}            /* DataDog extension: Constant tags, always applied */
  );

  public static final void main(String[] args) {
    statsd.event("title", "message");
  }
}
```
