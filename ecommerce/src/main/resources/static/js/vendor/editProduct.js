$(document).ready(function() {
    // showSubCategory();
    var categoryId = $("#category-select option:selected").val();
    var childs = $('#sub-category-select').children();
    childs.each( function () {
        if ($(this).attr('data-categoryId') == categoryId) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
});

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
            $('.custom-select2 select').val($(this).val());
            return false;
        }
    })
}