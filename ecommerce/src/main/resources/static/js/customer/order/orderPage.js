var transporterId;
var shippingFee;
// var totalTemp;
$(document).ready(function(){
    addShippingFee();
    $('input[type=radio][name=transporterId]').change(function() {
        addShippingFee();
    });
})

function addShippingFee() {
    transporterId = $('input[type=radio][name=transporterId]:checked').val();
    shippingFee = Number($('#shippingFeeValue'+transporterId).val());
    // totalTemp = Number($('#totalTemp').val());
    $('#shippingFeeDsp').text(shippingFee);
    $('#shippingFee').val(shippingFee);
    $('#total').text((shippingFee + totalTemp).valueOf());
    money2();
}

function money2() {
    $('.moneyTemp').each(function() {
        $(this).text(commafy($(this).text()));
    });
}