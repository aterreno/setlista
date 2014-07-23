var findTrack = function(song, artistName) {
    $.ajax({
        url: 'https://api.spotify.com/v1/search',
        data: {
            q: artistName + " " + song["@name"],
            type: "track",
            limit: 1
        },
        success: function(response) {
            var trackId = _.first(response.tracks.items).id;
            var current = $("a#playlist").attr("href");
            $("a#playlist").attr("href", current.concat(",", trackId));
            $("a#playlist").show();


            var current = $('iframe').attr('src');
            $('iframe').attr('src', current.concat(",", trackId));
            $('iframe').show();
        }
    });
}
var searchSetList = function(artistName, date) {
    $.ajax({
        url: '/search/' + artistName + '/' + date,
        success: function(response) {
            _.each(response.setlists.setlist.sets.set, function(set) {
                _.each(set.song, function(song) {
                    findTrack(song, artistName)
                });
            });
        }
    });
};
$(function() {
    $('.datepicker').pickadate({
        format: 'dd-mm-yyyy',
        max: new Date()
    });
    $("form").validate({
        rules: {
            artist: {
                required: true
            },
            date: {
                required: true
            }
        },
        messages: {
            artist: {
                required: "Please enter the artist name"
            },
            date: {
                required: "Please up pick a date"
            }
        }
    });
    $("#search").submit(function(event) {
    	$("a#playlist").attr("href", "spotify:trackset:PlaylistName:");
    	$('iframe').attr('src', "https://embed.spotify.com/?uri=spotify:trackset:Setlister:");    	
        searchSetList($("input#artist").val(), $("input#date").val());
        event.preventDefault();
    });
});