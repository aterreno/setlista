var findSong = function(song) {
    console.log(song["@name"]);

}

var searchSetList = function (artistName, date) {
    $.ajax({
	url: '/search/' + artistName + '/' + date,

	success: function (response) {
	    _.each(response.setlists.setlist.sets.set, function(set) { _.each(set.song, findSong); });
	}
    });
};
