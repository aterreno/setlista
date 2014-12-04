[Trello url](https://trello.com/b/0ykvNW0h/setlista)

######Local run
[![Gitter](https://badges.gitter.im/Join Chat.svg)](https://gitter.im/aterreno/setlista?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
	git clone git@github.com:aterreno/setlista.git
	cd setlista

You will need [bower](http://bower.io/) installed to generate the client side code, once installed go to

	resources/public/
	
and run

	bower install
	
Setlista uses redis to store the latest searches from users, install [redis](http://redis.io/) and start it up.

You will need [leiningen](https://github.com/technomancy/leiningen) installed to run the server side code, once installed type: 
	
	lein run 
	
The app should be then running [on port 8000](http://locahost:8000)

######Deploy

	git push heroku master 

will deploy to heroku, and the domain http://www.setlista.com points to that instance

######Old 'Docs'

An example query to setlistfm:

	http://api.setlist.fm/rest/0.1/search/setlists.json?artistName=jayhawks&date=15-07-2014

With a bit of JS magic you can then query spotify for the songs returned: 

	https://api.spotify.com/v1/search?q=jayhawks I'm Gonna Make You Love Me

[ ![Codeship Status for aterreno/setlista](https://www.codeship.io/projects/2a7c10c0-2568-0132-1047-26ef7245ec1c/status)](https://www.codeship.io/projects/37144)