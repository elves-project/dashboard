(function($) {

    'use strict';

    $(document).ready(function() {

        $('#cp1').colorpicker();

        $('#cp2').colorpicker();

        $('#cp3').colorpicker({
            color: '#AA3399',
            format: 'rgb'
        });

        $('#cp4').colorpicker().on('changeColor', function(e) {
            $(this)[0].style.backgroundColor = e.color.toString( 'rgba');
        });

        $('#cp5').colorpicker({
            color: "transparent",
            format: "hex"
        });

        $('#cp6').colorpicker({
            color: "#88cc33",
            horizontal: true
        });

         $('#cp8').colorpicker({
             colorSelectors: { 'black': '#000000',
                               'white': '#ffffff',
                               'red': '#FF0000',
                               'default': '#777777',
                               'primary': '#337ab7',
                               'success': '#5cb85c',
                               'info': '#5bc0de',
                               'warning': '#f0ad4e',
                               'danger': '#d9534f' }
         });

         $('#cp11').colorpicker({
             color: "#4AA9E9"
         });

    });

})(window.jQuery);
