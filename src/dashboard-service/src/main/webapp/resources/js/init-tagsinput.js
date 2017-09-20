(function($) {

    'use strict';

    $(document).ready(function() {

        function onAddTag(tag) {
            alert("Added a tag: " + tag);
        }
        function onRemoveTag(tag) {
            alert("Removed a tag: " + tag);
        }

        function onChangeTag(input,tag) {
            alert("Changed a tag: " + tag);
        }

        $(function() {
            $('#tags_1').tagsInput({width:'auto'});
            $('#tags_2').tagsInput({
                width: 'auto',
                onChange: function(elem, elem_tags)
                {
                    var languages = ['php','ruby','javascript'];
                    $('.tag', elem_tags).each(function()
                    {
                        if($(this).text().search(new RegExp('\\b(' + languages.join('|') + ')\\b')) >= 0)
                        $(this).addClass('tag--yellow');
                    });
                }
            });
        });

    });

})(window.jQuery);
