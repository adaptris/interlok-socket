# interlok-socket [![GitHub tag](https://img.shields.io/github/tag/adaptris/interlok-socket.svg)](https://github.com/adaptris/interlok-socket/tags)
The suggested projectname was literate-waffle...

As part of the 3.7.0 release, we decided to the "roll your own protocol that works over TCP sockets" into its own package. It has a very limited use-case and doesn't really deserve to live in Interlok proper.

So here it is, if you were looking for it, then you can still depend on it via gradle/ivy/maven as usual.

```
compile ("com.adaptris:interlok-socket:3.7-SNAPSHOT") { changing= true}
```

```
<dependency org="com.adaptris" name="interlok-socket" rev="3.7-SNAPSHOT" conf="runtime->default" changing="true"/>
```

```
<dependency>
  <groupId>com.adaptris</groupId>
  <artifactId>interlok-socket</artifactId>
  <version>3.7-SNAPSHOT</version>
</dependency>
```
