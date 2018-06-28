### Make A Baking App

Created as a part of [Udacity Android Developer Nanodegree Program](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801).

The app uses [Google's Custom Search JSON API](https://developers.google.com/custom-search/) to get recipe
images in order to handle lack of server data.

To use the app add a file named apikey.properties at the root project directory containing the string
googleapikey="xxx"
where xxx is a valid Google API key

The app follows the master/detail flow design concept and makes use of fragments.
In tablets, a master list fragment causes fragment transactions and replaces a media and a step
description fragment which are present in the layout.
In mobile phones, the master list fragment directs the user to a separate activity. The latter uses
a ViewPager which allows the user to navigate between recipe steps by swiping. These steps are now
fragments that include the aforementioned media and step description fragments as nested fragments.

A collection widget lists the ingredients of the last visited recipe.