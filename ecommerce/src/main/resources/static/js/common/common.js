var CommonPartsJs = {
    redirectTo: function redirectTo(redirectUrl, dataRequest) {
        const form = $("#common-redirect-form");
        if (redirectUrl && (dataRequest === undefined || dataRequest === null)) {
            form.attr('action', redirectUrl);
            form.submit();
        } else if (redirectUrl && dataRequest && typeof dataRequest === "object") {
            const keys = Object.keys(dataRequest);
            for (let i = 0; i < keys.length; i++) {
                const key = keys[i];
                const value = dataRequest[key];
                $('<input>').attr({
                    type: 'hidden',
                    id: key,
                    name: key,
                    value: value
                }).appendTo(form);
            }
            form.attr('action', redirectUrl);
            form.submit();
        }
    },
}

$(function () {
    $.showLoading = function() {
        $('body').showLoading();
    }

    $.closeLoading = function() {
        $('body').closeLoading();
    }
})

$(document).on({
    ajaxStart: function(){
        $("body").addClass("loading");
    },
    ajaxStop: function(){
        $("body").removeClass("loading");
    }
});

$(document).ready(function() {
    $.fn.rating.defaults.filled = 'fas fa-star';
    $.fn.rating.defaults.empty = 'far fa-star';
    $('.money').each(function() {
        $(this).text(commafy($(this).text()));
    });

    $( ".card" ).hover(
        function() {
            $(this).addClass('shadow-lg').css('cursor', 'pointer');
        }, function() {
            $(this).removeClass('shadow-lg');
        });

    $('#dtBasicExample').DataTable();
    $('.dataTables_length').addClass('bs-select');

    $('#rateMe1').mdbRate();
    // $('.rating').rating({
    //     filled: 'fas fa-star star-filled',
    //     empty: 'far fa-star'
    // });
});

function commafy(num) {
    return num === '' ? num : num.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,')+ ' đ';
}
