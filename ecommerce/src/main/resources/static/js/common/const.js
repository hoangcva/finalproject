const SIZE_5_MB = 5242880;


var message = {}

function getMessage(key, argument) {
    var str = message[key];
    if (Array.isArray(argument)) {
        for ( var k in argument) {
            str = str.replace("{" + k + "}", argument[k]);
        }
        return str;
    } else {
        return str.replace("{0}", argument);
    }
}

