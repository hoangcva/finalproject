$(document).ready(function() {
    showDistrict();
    showWard();
});

$(document).on('change', '#province-select',  function () {
    showDistrict();
});

$(document).on('change', '#district-select', function() {
    showWard();
})

function showDistrict() {
    var provinceId = $("#province-select option:selected").val();
    var districts = $('#district-select').children();
    districts.each( function () {
        if ($(this).attr('data-provinceId') == provinceId) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
    districts.each( function () {
        if ($(this).attr('data-provinceId') == provinceId) {
            $('.custom-select2 select').val($(this).val());
            return false;
        }
    })
}

function showWard() {
    var districtId = $("#district-select option:selected").val();
    var wards = $('#ward-select').children();
    wards.each( function () {
        if ($(this).attr('data-districtId') == districtId) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
    wards.each( function () {
        if ($(this).attr('data-districtId') == districtId) {
            $('.custom-select3 select').val($(this).val());
            return false;
        }
    })
}