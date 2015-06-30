# Benchmarker <img src="https://cloud.githubusercontent.com/assets/5122822/8385821/b88ae08e-1c68-11e5-871f-5dafca6d4b26.png" height="60" width="60"/>
Why rely on others benchmarking reports when you can test it out in your own device? Welcome to Benchmarker, your one stop app for micro-benchmarking databases and comparing performance statistics on the go, so that you can make a well-informed decision in your next superhit app.

## Motivation
Test and publish a detailed performance report on various databases and ORM's supported on the Android Platform such that developers can make an informed choice

## Roadmap
* Generate performance / throughput test cases for db types
* Monitor and report the performance statistics for comparision
* Full Material Design

## Target DB/ORM's
### Databases
- [x] [Android SQLite](http://developer.android.com/training/basics/data-storage/databases.html)
- [x] [Realm](http://realm.io)
- [x] [SnappyDB](https://github.com/nhachicha/SnappyDB)
- [x] [Couchbase Lite](http://developer.couchbase.com/mobile/index.html)

### ORM's *(based on SQLite)*
- [ ] [GreenDAO](http://greendao-orm.com)
- [ ] [OrmLite](http://ormlite.com/sqlite_java_android_orm.shtml)
- [ ] [SugarORM](http://satyan.github.io/sugar/index.html)
- [ ] [ActiveAndroid](http://www.activeandroid.com/)
- [ ] [DBFlow](https://github.com/Raizlabs/DBFlow)


*Checkmarks indicate test cases updated till now*

**NB** : My main motivation was to test out the performance of sqlite in comparision with several new and upcoming nosql databases. If you would like to help me out setting up the various ORM tests, view this [issue](https://github.com/koustuvsinha/benchmarker/issues/18)

## Further Planning
* Display detailed performance test report on separate webpage
* Publish in Google Play
* Real Time performance updates

## Results
You can find the initial benchmarking results in [here](RESULTS.md)

## Contributing
Being a newcomer into the Android World, contributions of any form are wholeheartedly welcome!
Read the [Contributing Guidelines](DEVELOPMENT.md#contribution-guidelines) and if you are a first time Android Developer read the [Tools to download](DEVELOPMENT.md#tools-to-download)

## Credits
Thanks to the projects mentioned in the [dependencies](DEVELOPMENT.md#dependencies) section for providing such amazing libraries to work with!

## License
[MIT](LICENSE.md)
