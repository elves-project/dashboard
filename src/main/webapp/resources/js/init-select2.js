(function($) {

    'use strict';

    $(document).ready(function() {
        $(".js-example-basic-select2").select2({
            placeholder: "Select a state"
        });

        // Templating
        function formatState (state) {
            if (!state.id) { return state.text; }
            var $state = $(
                '<span><img src="bower_components/select2/docs/vendor/images/flags/' + state.element.value.toLowerCase() + '.png" class="img-flag" /> ' + state.text + '</span>'
            );
            return $state;
        };
        $(".js-example-templating").select2({
            placeholder: "Select a state",
            templateResult: formatState,
            templateSelection: formatState,
            allowClear: true
        });

        // Loading array data
        var data = [{ id: 0, text: 'Enhancement' },
                    { id: 1, text: 'Bug' },
                    { id: 2, text: 'Duplicate' },
                    { id: 3, text: 'Invalid' },
                    { id: 4, text: 'Wontfix' }];

        $(".js-example-data-array").select2({
            data: data
        });

        // Loading remote data
        function formatRepo (repo) {
            if (repo.loading) return repo.text;

            var markup = "<div class='select2-result-repository clearfix'>" +
            "<div class='select2-result-repository__avatar'><img src='" + repo.owner.avatar_url + "' /></div>" +
            "<div class='select2-result-repository__meta'>" +
            "<div class='select2-result-repository__title'>" + repo.full_name + "</div>";

            if (repo.description) {
                markup += "<div class='select2-result-repository__description'>" + repo.description + "</div>";
            }

            markup += "<div class='select2-result-repository__statistics'>" +
            "<div class='select2-result-repository__forks'><i class='fa fa-flash'></i> " + repo.forks_count + " Forks</div>" +
            "<div class='select2-result-repository__stargazers'><i class='fa fa-star'></i> " + repo.stargazers_count + " Stars</div>" +
            "<div class='select2-result-repository__watchers'><i class='fa fa-eye'></i> " + repo.watchers_count + " Watchers</div>" +
            "</div>" +
            "</div></div>";

            return markup;
        }

        function formatRepoSelection (repo) {
            return repo.full_name || repo.text;
        }

        $(".js-data-example-ajax").select2({
            placeholder: "Choose your github repo",
            ajax: {
                url: 'https://api.github.com/search/repositories',
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        q: params.term, // search term
                        page: params.page
                    };
                },
                processResults: function (data) {
                    return {
                        results: data.items
                    };
                },
                processResults: function (data, params) {
                   // parse the results into the format expected by Select2
                   // since we are using custom formatting functions we do not need to
                   // alter the remote JSON data, except to indicate that infinite
                   // scrolling can be used
                   params.page = params.page || 1;

                   return {
                       results: data.items,
                       pagination: {
                           more: (params.page * 30) < data.total_count
                       }
                   };
               },
               cache: true
           },
           escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
           minimumInputLength: 1,
           templateResult: formatRepo, // omitted for brevity, see the source of this page
           templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
        });


    });

})(window.jQuery);
