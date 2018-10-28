app.filter('isEmpty', function() {
        return function(input) {
            if (input==undefined || input=='') {
                return true;
            }
            return false;
        };
    })
