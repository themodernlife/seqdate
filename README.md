seqdate
-------

A little utility for generating time series.  Example

```bash
$> seqdate -o YYYY-MM-dd-HH 2013-01-01 2013-03-01 6 hours
2013-01-01-00
2013-01-01-06
2013-01-01-12
2013-01-01-18
2013-01-02-00
2013-01-02-06
...
```

Build via `sbt` with the `sbt-native-packager` plugin.

```bash
$> sbt universal:package-bin
```
