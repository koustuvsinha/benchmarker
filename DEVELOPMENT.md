# Development Guide
This guide will show you how to setup your local machine to build and work on this application, if you are a first time Android Developer

## Tools to download
* [Android Studio](http://developer.android.com/tools/studio/index.html) (Recommended, but you can also work in [Eclipse](http://developer.android.com/sdk/installing/installing-adt.html) if you prefer)
* Android Studio comes bundled with Android SDK Tools. Put the path to "adb" in your local machine path. ADB or Android Debug Bridge will be used to test our app in real devices
* Open the Android SDK Manager (in Android Studio, Tools -> Android -> SDK Manager) and download the dependencies. For this project, make sure you have : 
  * Android SDK Tools Rev 24+
  * Android Platform Tools 22
  * Android SDK Build Tools 22+
  * Android 5.1.1 (API 22) -> SDK Platform (you are welcome to install more Android version sdks)
  * Android Support Library 22+
* By default Android Studio comes with Gradle v2.2. However, if you want you can use the latest Gradle 2.4, which is faster in execution. Goto File -> Project Structure -> Project -> Gradle Version, put 2.4 and then Sync Gradle
* Once you have setup the tools, star this repo, Fork it and Clone it in your local machine. You can either clone directly from Android Studio, or clone it before hand and then import it.

## Contribution Guidelines
* Read the existing issues, create issues if necessary
* Adhere to standard Git workflow. Before working on something, create a branch (do not work directly on the master branch, as it will lead to conflicts), commit some code and then push it to your fork. Then file a PR (Pull Request)
* You may want to update your fork time to time w.r.t to development in main branch. Do the following :
  * Switch to master `git checkout master`
  * Add remotes if not added yet `git remote add upstream https://github.com/koustuvsinha/benchmarker.git`
  * Check if proper remotes are added : `git remote -v` , it should display two origin remotes and two upstream remotes
  * Pull upstream `git pull upstream`
  * Rebase into master `git rebase upstream/master`
  * Now create your own branch and checkout
* Commit messages should be in present tense. Example, "Add ORMLite Test Case" and NOT "Added ORMLite Test Cases"
* Commit messages should be clear and concise.
* Commit often, but before pushing squash relevant commits. Suppose you have four similar functional commits, then do the following :
  * Check how many commits you need to squash `git log`
  * Squash the commits by interactive rebase `git rebase -i HEAD~2`, where 2 should be replaced by the number of commits you wish to squash
  * Leaving the top 'pick' line, change all the 'pick's to 'squash' in subsequent lines and save the file
  * Give a meaningful commit message
* Submit PR for review :)

## Useful Resources
The following useful resources I consult for development :
* [Android Official Developer Resources](http://developer.android.com/training/index.html)
* [CodePath Android Guides](https://guides.codepath.com/android)
* [Curated List of Awesome UI Libraries](https://github.com/wasabeef/awesome-android-ui)
* [Material Pallette](http://www.materialpalette.com/)
* [Android Launcher Icon generator](https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html)

## Dependencies
The following are the list of dependencies of this application (which you will also find listed in [build.gradle](app/build.gradle) file)
* [Crashlytics](https://fabric.io) - Automatic crash reporting toolkit
* [FutureSimple's Floating Action Button](https://github.com/futuresimple/android-floating-action-button) - great library to render android Material Floating Action buttons
* [Andy Gibson's DataFactory](https://github.com/andygibson/datafactory) - Java library to generate fake data
* [Wasabeef's RecyclerView Animator](https://github.com/wasabeef/recyclerview-animators) - Generate RecyclerView custom animations
* [Material Dialogs](https://github.com/afollestad/material-dialogs) - a beautiful customizable Material design dialog generator
* [SquareUp Otto](http://square.github.io/otto/) - High performance Android event bus
* [CircleProgess](https://github.com/lzyzsd/CircleProgress) - CleanerMaster style Circle Progress library
* [PhilJay's MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) - amazingly powerful and customizable Android Chart Library
* [AmulyaKhare's TextDrawable](https://github.com/amulyakhare/TextDrawable) - Gmail Style Letter Images generator library

Huge thanks to the devs for providing such high quality free software :)
