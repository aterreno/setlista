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
            var current = $('iframe#spotify').attr('src');
            $('iframe#spotify').attr('src', current.concat(",", trackId));
            $('iframe#spotify').show();
        }
    });
};
var searchSetList = function(artistName, date) {
    $.ajax({
        url: '/search/' + artistName + '/' + date,
        success: function(response) {              
            var setlist = response.setlists.setlist;
            if (!_.isArray(setlist)) {
                setlist = [setlist]; //need to wrap the result of the API call in an array otherwise we will loop on its fields #fuckme
            }
            _.each(setlist, function(set) {
                
                $.get('templates/result.mst', function(template) {
                    var rendered = Mustache.render(template, {
                        id: set["@id"],
                        artist: set.artist["@name"],
                        venue: set.venue["@name"],
                        city: set.venue.city["@name"],
                        state: set.venue.city["@state"],
                        date: moment(set["@eventDate"], "DD-MM-YYYY").format("dddd, MMMM Do YYYY")
                    });
                    $('#target').append(rendered);
                    $('#' + set["@id"]).click(set, function(evt) {                        
                        reset();
                        _.each(set.sets.set, function(set) {                            
                            _.each(set.song, function(song) {
                                findTrack(song, artistName);
                            });
                        });
                        $('iframe#spotify').show();
                    });
                });
            });
        },
        error: function(response) {
            $("span.error").show();
            $("span.error").text("Sorry I can't find this gig");
        }
    });
};
var reset = function() {
    $("#target").html("");
    $("span.error").hide();
    $("a#playlist").attr("href", "spotify:trackset:PlaylistName:");
    $('iframe#spotify').attr('src', "https://embed.spotify.com/?uri=spotify:trackset:setlista:");
    $('iframe#spotify').hide();
}
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
                required: false
            }
        },
        messages: {
            artist: {
                required: "Please enter the artist name or the venue"
            },
            date: {
                required: "Please up pick a date"
            }
        }
    });
    $("#search").submit(function(event) {
        reset();
        searchSetList($("input#artist").val(), $("input#date").val());
        event.preventDefault();
    });
});