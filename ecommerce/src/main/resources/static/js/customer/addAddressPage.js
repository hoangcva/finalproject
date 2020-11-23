$(document).ready(function() {
    showDistrict();
    showWard();
});

$(document).on('change', '#province-select',  function () {
    showDistrict();
    var province = $('#province-select option:selected').text();
    $('#province').val(province);
});

$(document).on('change', '#district-select', function() {
    showWard();
    var district = $('#district-select option:selected').text();
    $('#district').val(district);
})

$(document).on('change', '#ward-select', function() {
    var ward = $('#ward-select option:selected').text();
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
    $('#district-select').val('');
    $('#ward-select').val('');
}

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
    $('#ward-select').val('');
}