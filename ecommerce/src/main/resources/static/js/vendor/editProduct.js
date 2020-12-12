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

    $('#imageFile1').change(function() {
        showImageThumbnail(this, '#thumbnail1', '#add-img-btn1', '#delete-btn-img1', '#previous-img-1');
    });
    $('#imageFile2').change(function() {
        showImageThumbnail(this, '#thumbnail2', '#add-img-btn2', '#delete-btn-img2', '#previous-img-2');
    });
    $('#imageFile3').change(function() {
        showImageThumbnail(this, '#thumbnail3', '#add-img-btn3', '#delete-btn-img3', '#previous-img-3');
    });

    $('#add-img-btn1, #thumbnail1, #previous-img-1').click(function() {
        $('#imageFile1').click();
    });
    $('#add-img-btn2, #thumbnail2, #previous-img-2').click(function() {
        $('#imageFile2').click();
    });
    $('#add-img-btn3, #thumbnail3, #previous-img-3').click(function() {
        $('#imageFile3').click();
    });

    $('#delete-btn-img1').click(function() {
        removeImage('#imageFile1', '#thumbnail1', '#add-img-btn1', this, '#previous-img-1','#delete1');
    });
    $('#delete-btn-img2').click(function() {
        removeImage('#imageFile2', '#thumbnail2', '#add-img-btn2', this, '#previous-img-2', '#delete2');
    });
    $('#delete-btn-img3').click(function() {
        removeImage('#imageFile3', '#thumbnail3', '#add-img-btn3', this, '#previous-img-3', '#delete3');
    });

    $('.btn-save').click(function() {
        $('#save-product-form').submit();
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
            $('.custom-select-div2 select').val($(this).val());
            return false;
        }
    })
};

function showImageThumbnail(fileInput, thumbnailId, addImgBtnId, deleteImgBtnId, previousImg) {
    file = fileInput.files[0];
    if(file.size >= SIZE_5_MB) {
        handleOverSizeImage(fileInput, thumbnailId);
        return;
    }
    reader = new FileReader();
    reader.onload = function(e) {
        $(thumbnailId).attr('src', e.target.result);
        $(thumbnailId).show();
        $(deleteImgBtnId).show();
        $(addImgBtnId).hide();
        $(previousImg).hide();
    }
    reader.readAsDataURL(file);
};

function handleOverSizeImage(fileInput, thumbnailId) {
    alert("Please choose another image");
    fileInput.value = '';
    $(thumbnailId).hide();
};

function removeImage(fileInput, thumbnailId, addImgBtnId, deleteImgBtnId, previousImg, isDelete) {
    $(addImgBtnId).show();
    $(deleteImgBtnId).hide();
    $(thumbnailId).hide();
    $(fileInput).val(null);
    $(previousImg).hide();
    $(isDelete).val('true');
    $(isDelete).value = 'true';
};
