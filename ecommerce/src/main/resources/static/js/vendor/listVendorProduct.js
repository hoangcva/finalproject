$(document).ready(function (){
    // $('input[type=radio][name=exampleRadios]').change(function() {
    //     var data;
    //     if (this.value == 'all') {
    //         data = { 'type':'all'}
    //     }
    //     else if (this.value == 'deactivated') {
    //         data = { 'type':'deactivated'}
    //     }
    //     else if (this.value == 'active') {
    //         data = { 'type':'active'}
    //     };
    //
    //     $.get("/admin/account/vendor",data).done(function(fragment) {
    //         $("#content").html(fragment);
    //     });
    // });
})

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

function deactivateProduct(productId, enable)
{
    var radioType = $('input[type=radio][name=exampleRadios]:checked').val();
    let result = confirm("Do you want to deactivate this product?");
    var vendorForm = {
        "productId" : productId,
        "enable" : enable,
        "radioType" : radioType
    };
    if (result) {
        $.ajax({
            type:'POST',
            url: '/vendor/product/activate',
            // headers: { 'X-CSRF-TOKEN': $("input[name='_csrf']").val()},
            contentType: 'application/json',
            dataType : 'json',
            data: JSON.stringify(vendorForm),
            success : function (result) {
                $("#content").html(result.responseText);
            },
            error: function (result) {
                $("#content").html(result.responseText);
            },
        });
    }
}