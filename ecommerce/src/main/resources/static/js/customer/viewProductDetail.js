$(document).ready(function () {
    var quantity = parseInt($('#quantity').val(), 10);
    $('.change-quantity').on('change', '.buy-quantity', function () {
        var buyQuantity = parseInt($('.buy-quantity').val(), 10);
        if (buyQuantity <= quantity) {
            //hide warning
            hideWarning();
        } else {
            //show warning
            showWarning();
        }
        $('.buy-quantity').val(quantity);
    });

    $('.change-quantity').on('click', '#decrease-btn', function () {
        var buyQuantity = parseInt($('.buy-quantity').val(), 10);
        if (buyQuantity > 0) {
            buyQuantity = buyQuantity - 1;
        }
        if (buyQuantity <= quantity) {
            //hide warning
            hideWarning();
        }
        $('.buy-quantity').val(buyQuantity);
    });

    $('.change-quantity').on('click', '#increase-btn', function () {
        var buyQuantity = parseInt($('.buy-quantity').val(), 10);
        if (buyQuantity < quantity) {
            buyQuantity = buyQuantity + 1;
        } else {
            //show warning
            showWarning();
        }
        $('.buy-quantity').val(buyQuantity);
    });
});

function showWarning() {
    $('#over-quantity').removeClass('d-none');
    $('#over-quantity').removeClass('none');
    $('#over-quantity').addClass('d-block');
    $('#over-quantity').addClass('show');
}

function hideWarning() {
    $('#over-quantity').removeClass('d-block');
    $('#over-quantity').removeClass('show');
    $('#over-quantity').addClass('d-none');
    $('#over-quantity').addClass('none');
}

function addProductToCart() {
    var productId = $('#productId').val();
    var vendorId = $('#vendorId').val();
    var buyQuantity = $('.buy-quantity').val();
    var quantity = $('#quantity').val();

    var productForm = {
        productId: productId,
        vendorId: vendorId,
        quantity: quantity,
    };
    var dataRequest = {
        productForm: productForm,
        buyQuantity: buyQuantity,
    };
    $.ajax({
        type:'POST',
        url: '/customer/cart/add-product',
        // headers: { 'X-CSRF-TOKEN': $("input[name='_csrf']").val()},
        contentType: 'application/json',
        dataType : 'json',
        data: JSON.stringify(dataRequest),
        success : function (result) {
            alert(result.msg);
            alert(result.responseJSON.msg);
        },
        error: function (result) {
            alert(result.msg);
            alert(result.responseJSON.msg);
        },
    });
}


