function loadSearchResult()
{
    var keyword = $('#keyword').val();
    if (keyword.replace(/\s/g,'') === '') {
        alert("Please enter keyword!");
        return;
    }
    var data = {
        "keyword" : keyword
    }

    $.get("/vendor/product/view/search",data).done(function(fragment) { // get from controller
        $("#content").html(fragment);
    });
}