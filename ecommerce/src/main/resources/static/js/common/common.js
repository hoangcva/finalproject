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