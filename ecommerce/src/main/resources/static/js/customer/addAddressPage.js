var province;
var district;
var ward;
$(document).ready(function() {
    province = $('#province-select option:selected').text();
    district = $('#district-select option:selected').text();
    ward = $('#ward-select option:selected').text();
    if (ward == "Select ward") {
        ward = "";
    }
    showDistrict();
    showWard();
    $('#province').val(province);
    $('#district').val(district);
    $('#ward').val(ward);
});

$(document).on('change', '#province-select', function () {
    showDistrict();
    province = $('#province-select option:selected').text();
    $('#province').val(province);
    $('#district-select').val('');
    $('#ward-select').val('');
});

$(document).on('change', '#district-select', function() {
    showWard();
    district = $('#district-select option:selected').text();
    $('#district').val(district);
    $('#ward-select').val('');
    ward = "";
    $('#ward').val(ward);
})

$(document).on('change', '#ward-select', function() {
    ward = $('#ward-select option:selected').text();
    if (ward == "Select ward") {
        ward = "";
    }
    $('#ward').val(ward);
})

function showDistrict() {
    var provinceId = $("#province-select option:selected").val();
    if (provinceId == '') {
        $('.default-option').show();
        $('#district-select').val('');
        $('#ward-select').val('');
        return;
    }
    var districts = $('#district-select').children();
    districts.each( function () {
        if ($(this).attr('data-provinceId') == provinceId) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
};

function showWard() {
    var districtId = $("#district-select option:selected").val();
    if (districtId == '') {
        $('.default-option').show();
        $('#ward-select').val('');
        return;
    }
    var wards = $('#ward-select').children();
    wards.each( function () {
        if ($(this).attr('data-districtId') == districtId) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
}