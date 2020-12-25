function remove(favoriteId) {
    var data = { 'favoriteId' : favoriteId}
    $.get("/customer/favorite/remove",data).done(function(fragment) {
        $("#content").html(fragment);
    });
}