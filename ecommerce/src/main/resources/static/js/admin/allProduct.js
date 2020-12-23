$(document).ready(function (){
    showSubCategory();
    $('input[type=radio][name=exampleRadios]').change(function() {
        var data;
        if (this.value == 'all') {
            data = { 'radioType':'all'}
        }
        else if (this.value == 'deactivated') {
            data = { 'radioType':'deactivated'}
        }
        else if (this.value == 'active') {
            data = { 'radioType':'active'}
        };

        $.get("/admin/product/view/list/search",data).done(function(fragment) {
            $("#content").html(fragment);
        });
    });
})

function loadSearchResult()
{
    var categoryId = $("#category-select option:selected").val();
    var subCategoryId = $("#sub-category-select option:selected").val();
    var radioType = $('input[type=radio][name=exampleRadios]:checked').val();
    var keyword = $('#keyword').val();

    var data = {
        "keyword" : keyword,
        "categoryId" : categoryId,
        "subCategoryId" : subCategoryId,
        "radioType" : radioType
    }

    $.get("/admin/product/view/list/search",data).done(function(fragment) { // get from controller
        $("#content").html(fragment);
    });
}

function deactivateProduct(productId,vendorId, enable)
{
    var radioType = $('input[type=radio][name=exampleRadios]:checked').val();
    let result = confirm("Do you want to deactivate this product?");
    var vendorForm = {
        "productId" : productId,
        "vendorId" : vendorId,
        "enable" : enable,
        "radioType" : radioType
    };
    if (result) {
        $.ajax({
            type:'POST',
            url: '/admin/product/activate',
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

$(document).on('change', '#category-select',  function () {
    showSubCategory();
});

function showSubCategory() {
    var categoryId = $("#category-select option:selected").val();
    var childs = $('#sub-category-select').children();
    childs.each( function () {
        if ($(this).attr('data-categoryId') == categoryId) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });

    childs.each( function () {
        if ($(this).attr('data-categoryId') == categoryId) {
            $('.custom-select-div2 select').val($(this).val());
            return false;
        }
    })
}