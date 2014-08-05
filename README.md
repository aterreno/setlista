######The problem

I usually go to a concert, come back home and want to listen again to the gigs songs. 
Or, I get invited to a gig of a band I don't know and I want to check out their music. 

######Solution? 

Connect setlist.fm API to Spotify. 

An example query to setlistfm:

	http://api.setlist.fm/rest/0.1/search/setlists.json?artistName=jayhawks&date=15-07-2014

With a bit of JS magic you can then query spotify for the songs returned: 

	https://api.spotify.com/v1/search?q=jayhawks I'm Gonna Make You Love Me


######Todo

* go private on git
* better ui :-)
* save plalyst as separate button / feature will ask to log-in
* have a separate spotify account for the app and save some 'glorious concerts plalysts' there
* show last searches
* make play button work on mobile
* cloudfront or nginx for static content, bit silly to use compojure for that
* autocomplete for artists
* pivot/other useful stuff around playlists: find artists/songs in a page and produce a playlist
* integrate with (rapgenius) for lyrics
* integrate with MusiXMatch for karaoke
* most played by artist, least played by artis
* top searches
* what are users searching right now
* make search result unique, so can share the result


