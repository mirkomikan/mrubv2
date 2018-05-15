# mrubv2
MRU-B-v2-ContactsViewLoaderCallbacks

MRU-B v2 CONTACTS VIEW, Specs part 8 
------------------------------------
CustomContactAdapter extends BaseAdapter  
LoaderCallbacks<Cursor>  
ListView lstContact   

DONE:
+ View that lists all the phone’s Contacts from LoaderCallbacks<Cursor> 
+ Contact shows name, picture if available and a phone number.
+ Contact's photo showing fine (square though, couldn't make it circular without using 
3rd party libraries)
+ Show Contacts Access Permission dialog until accepted, if dismissed cancel view.

TODO:
- Problem with Tapping the cell should bring up the native phone’s Dialer with that contact’s number.
