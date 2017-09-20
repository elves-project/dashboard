(function($) {

    'use strict';

    $(document).ready(function() {

        // Then attach the picker to the element you want to trigger it
        $('input[name="daterange"]').daterangepicker();

        // Date and Time
        // You can customize Date Range Picker with options (http://www.daterangepicker.com/#options),
        // and get notified when the user chooses new dates
        // by providing a callback function.
        $(function() {
            $('input[name="daterangeWithTime"]').daterangepicker({
                timePicker: true,
                timePickerIncrement: 30,
                locale: {
                    format: 'MM/DD/YYYY h:mm A'
                }
            });
        });

        // Single Date Picker
        // The Date Range Picker can be turned into a single date picker widget with only one calendar.
        // In this example, dropdowns to select a month and year have also been enabled at the top of
        // the calendar to quickly jump to different months.
        $(function() {
            $('input[name="birthdate"]').daterangepicker({
                    singleDatePicker: true,
                    showDropdowns: true
                },
                function(start, end, label) {
                    var years = moment().diff(start, 'years');
                    alert("You are " + years + " years old.");
            });
        });

        // Predefined Ranges
        // This example shows the option to predefine date ranges that the user can choose from a list.
        $(function() {

            var start = moment().subtract(29, 'days');
            var end = moment();

            function cb(start, end) {
                $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
            }

            $('#reportrange').daterangepicker({
                startDate: start,
                endDate: end,
                ranges: {
                   'Today': [moment(), moment()],
                   'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                   'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                   'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                   'This Month': [moment().startOf('month'), moment().endOf('month')],
                   'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                }
            }, cb);

            cb(start, end);

        });

    });

})(window.jQuery);
