(function($) {

    'use strict';

    $(document).ready(function() {

        // switchery

        var elem = document.querySelector('.js-switch');
        var switchery = new Switchery(elem, { color: '#23b9a9' });

        var elem_2 = document.querySelector('.js-switch_2');
        var switchery_2 = new Switchery(elem_2, { color: '#4aa9e9' });

        var elem_3 = document.querySelector('.js-switch_3');
        var switchery_3 = new Switchery(elem_3, { color: '#eac459' });

    });

})(window.jQuery);
