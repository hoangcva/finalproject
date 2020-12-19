function showSubCategoryMenu(id) {
        $('.show-content'+id).addClass("show");
        // $('.dropdown-content '+id).style.display = 'block';
}

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

        $.get("/product/view/list/search",data).done(function(fragment) { // get from controller
                $("#content").html(fragment);
        });
}

function loadSearchResultCategory(categoryId, subCategoryId)
{
        var data = {
                "categoryId" : categoryId,
                "subCategoryId" : subCategoryId
        }

        $.get("/product/view/byCategory",data).done(function(fragment) { // get from controller
                $("#content").html(fragment);
        });
}