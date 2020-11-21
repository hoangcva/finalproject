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
            $('.buy-quantity').val(quantity);
        }
        updateCart();
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
        updateCart();
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
        updateCart();
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

function updateCart() {
    var id = $('#cartLineId').val();
    var buyQuantity = $('.buy-quantity').val();

    var dataRequest = {
        id: id,
        buyQuantity: buyQuantity,
    };
    $.ajax({
        type:'POST',
        url: '/user/updateCart',
        // headers: { 'X-CSRF-TOKEN': $("input[name='_csrf']").val()},
        contentType: 'application/json',
        dataType : 'json',
        data: JSON.stringify(dataRequest),
        success : function (result) {
            alert(result.msg);
            CommonPartsJs.redirectTo("/user/viewCart")
        },
        error: function (result) {
            alert(result.msg);
        },
    });
}


