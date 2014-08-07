var reloadHistory = function() {
    $.get('/templates/history.mst', function(template) {
        $.get("/search-history", function(data) {
            _.each(data, function(history) {
                var eventDate = moment(history.date, "DD-MM-YYYY");
                var date = eventDate.isValid() ? eventDate.format("dddd, MMMM Do YYYY") : "";
                var rendered = Mustache.render(template, {
                    artist: history.artist,
                    rawDate: history.date,
                    date: date
                });
                $('#history').append(rendered);
                $("a[href='/gig/" + history.artist + "/" + history.date + "']").click(function(evt) {
                    evt.preventDefault();
                    $("input#artist").val(history.artist);
                    $("input#date").val(history.date);
                    History.replaceState(null, "SetLista", $(this).attr('href'));
                });
            });
        });
    });
};
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
            var current = $('iframe#spotify').attr('src');
            $('iframe#spotify').attr('src', current.concat(",", trackId));
            $('iframe#spotify').show();            
        }
    });
};
var searchSetList = function(artistName, date) {
    $("#target").html("");
    $.ajax({
        url: '/search/' + artistName + '/' + date,
        success: function(response) {
            var setlist = response.setlists.setlist;
            if (!_.isArray(setlist)) {
                setlist = [setlist]; //need to wrap the result of the API call in an array otherwise we will loop on its fields #fuckme
            }
            _.each(setlist, function(set) {
                $.get('/templates/result.mst', function(template) {
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
                        $('h5#player').show();
                        $('h5#results').show();
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
    $("span.error").hide();
    $('iframe#spotify').attr('src', "https://embed.spotify.com/?uri=spotify:trackset:setlista:");
    $('iframe#spotify').hide();
};
$(function() {
    
    var urlChunks = _.last(document.URL.split('/'), 2);
    $("input#artist").val(decodeURI(_.first(urlChunks)));
    $("input#date").val(decodeURI(_.last(urlChunks)));

    reloadHistory();
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
        searchSetList($("input#artist").val(), $("input#date").val());
        History.replaceState(null, "SetLista", "/gig/" + $("input#artist").val() + "/" + $("input#date").val());
        event.preventDefault();
    });
});