(function($) {

    'use strict';

    $(document).ready(function() {

        $('.js-datepicker').datepicker({
            autoclose: true
        });

        $('http://megadin.lab.themebucket.net/assets/js/.input-group.date').datepicker({
            autoclose: true,
            todayHighlight: true
        });

        $('.input-daterange').datepicker({
            autoclose: true
        });

    });

})(window.jQuery);
