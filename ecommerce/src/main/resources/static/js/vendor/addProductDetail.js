var file
var reader
$(document).ready(function () {
    $('#imageFile1').change(function() {
        showImageThumbnail(this, '#thumbnail1', '#add-img-btn1', '#delete-btn-img1');
    });
    $('#imageFile2').change(function() {
        showImageThumbnail(this, '#thumbnail2', '#add-img-btn2', '#delete-btn-img2');
    });
    $('#imageFile3').change(function() {
        showImageThumbnail(this, '#thumbnail3', '#add-img-btn3', '#delete-btn-img3');
    });

    $('#add-img-btn1').click(function() {
        $('#imageFile1').click();
    });
    $('#add-img-btn2').click(function() {
        $('#imageFile2').click();
    });
    $('#add-img-btn3').click(function() {
        $('#imageFile3').click();
    });

    $('#thumbnail1').click(function() {
        $('#imageFile1').click();
    });
    $('#thumbnail2').click(function() {
        $('#imageFile2').click();
    });
    $('#thumbnail3').click(function() {
        $('#imageFile3').click();
    });

    $('#delete-btn-img1').click(function() {
        removeImage('#imageFile1', '#thumbnail1', '#add-img-btn1', this)
    });
    $('#delete-btn-img2').click(function() {
        removeImage('#imageFile2', '#thumbnail2', '#add-img-btn2', this)
    });
    $('#delete-btn-img3').click(function() {
        removeImage('#imageFile3', '#thumbnail3', '#add-img-btn3', this)
    });

    // $('#upload-btn').click(function() {
    //     $('form').submit(function() {
    //         var val = $('#imageFile1').val();
    //         if(val == null || val == '') {
    //             alert('Cover');
    //             return;
    //         }
    //     });
    // });
});

function showImageThumbnail(fileInput, thumbnailId, addImgBtnId, deleteImgBtnId) {
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
    }
    reader.readAsDataURL(file);
};

function handleOverSizeImage(fileInput, thumbnailId) {
    alert("Please choose another image");
    fileInput.value = '';
    $(thumbnailId).hide();
};

function removeImage(fileInput, thumbnailId, addImgBtnId, deleteImgBtnId) {
    $(addImgBtnId).show();
    $(deleteImgBtnId).hide();
    $(thumbnailId).hide();
    $(fileInput).val(null);
};