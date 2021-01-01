$(document).ready(function (){
})

function loadSearchResult()
{
    var keyword = $('#keyword').val();
    var vendorId = $('#vendorId').val();
    var data = {
        "keyword" : keyword,
        "vendorId" : vendorId
    }

    $.get("/customer/vendor-product/view/search",data).done(function(fragment) { // get from controller
        $("#content").html(fragment);
    });
}
