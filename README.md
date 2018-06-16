### Make A Baking App

Udacity Coursework

In order to handle lack of server data, the app uses a google custom search engine to get recipe
images that are not present in the jsons. In order to use the app add a file named apikey.properties
at the root project directory. This file must include a line similar to this:
googleapikey="xxx"
where xxx is a valid Google API key

The app makes heavy use of fragments when viewing recipe steps.

In tablets, a master list fragment causes fragment transactions and replaces a media and a step
description fragment which are present in the layout.

In mobile phones, the master list fragment directs the user to a separate activity. The latter uses
a ViewPager which allows the user to navigate between recipe steps by swiping. These steps are now
fragments that include the aforementioned media and step description fragments as nested fragments.
