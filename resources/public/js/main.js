var findTrack = function(song, artistName) {
    $.ajax({
	url: 'https://api.spotify.com/v1/search',
	data: { q: artistName + " " + song["@name"], type: "track", limit: 1},
	success: function (response) {	    
	    var trackId = _.first(response.tracks.items).id;
	    var current = $("a#playlist").attr("href");
	    $("a#playlist").attr("href", current.concat(",",trackId))
		}
    });
}

var searchSetList = function (artistName, date) {
    $.ajax({
	url: '/search/' + artistName + '/' + date,

	success: function (response) {
	    _.each(response.setlists.setlist.sets.set, function(set) { _.each(set.song, function(song) {findTrack(song, artistName)}); });
	}
    });
};

$(function() {

	$('.datepicker').pickadate({
	    format: 'dd-mm-yyyy'
	});

	$("#search").submit(function(event) {
		console.log($("input#date").val());
  		searchSetList($("input#artist").val(),$("input#date").val());
  		event.preventDefault();
	});		
});
