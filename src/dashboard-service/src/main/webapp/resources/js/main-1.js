/**
 * MegaDin v1.0.0 ()
 * Copyright 2017 ThemeBucket
 * Licensed under the ISC license
 */
;(function($) {
'use strict';
$(function() {

	    var $window = $(window),
        $windowWidth = $window.width(),
        $body = $('body'),
        $ui = $('#ui'),
        $content = $('#content'),
        $uiNav = $('[ui-nav]'),
        $uiDataNav = $('[data-toggle="ui-nav"]'),
        $uiDataMobileNav = $('[data-toggle="ui-nav--mobile-only"]'),
        $aside = $('#aside'),
        $asideRight = $('.ui-aside-right'),
        $uiDataAsideRight = $('[data-toggle="ui-aside-right"]');

    $uiDataNav.on('click', function(e){
        e.preventDefault();
        $ui.toggleClass('ui-aside-compact');
    });

    $uiDataAsideRight.on('click', function(e){
        e.preventDefault();
        $content.toggleClass('ui-content-compact');
    });

    // aside right overlay hide when click outside
    $body.on('click.asideRight', function(event){
        if ($asideRight.has(event.target).length == 0 && !$asideRight.is(event.target) && $uiDataAsideRight.has(event.target).length == 0 && !$uiDataAsideRight.is(event.target)){
            $content.removeClass('ui-content-compact');
        }
    });

    $window.on('resize.windowscreen', function() {
        var width = $(this).width();

        if (width < 768) {
            $body.on('click.aside', function(event) {
                if ($aside.has(event.target).length == 0 && !$aside.is(event.target) && $uiDataNav.has(event.target).length == 0 && !$uiDataNav.is(event.target)){
                    $ui.removeClass('ui-aside-compact');
                }
            });

            $uiDataMobileNav.on('click', function(e){
                e.preventDefault();
                $ui.removeClass('ui-aside-compact');
            });
        } else {
            $body.off('click.aside');
            $uiDataMobileNav.off('click');
        }
    });
    $window.trigger('resize.windowscreen');


    $uiNav.on('click', 'a', function(e) {

        // locate href
        // if there is no submenu
        var href = $(this).attr('href');
        if(href){
            window.location.href = href;
        }

        // Open submenu
        var $this = $(e.target), $active;
        $this.is('a') || ($this = $this.closest('a'));

        $active = $this.parent().siblings( ".active" );
        $active && $active.toggleClass('active').find('> ul:visible').stop().slideUp(200);

        ($this.parent().hasClass('active') && $this.next().stop().slideUp(200)) || $this.next().stop().slideDown(200);
        $this.parent().toggleClass('active');

        $this.next().is('ul') && e.preventDefault();
    });

    if($ui.hasClass('ui-aside-compact')) {
        var uiHasCompact = true;
    }

    if($content.hasClass('ui-content-compact')) {
        var uiContentHasCompact = true;
    }

    function doneResizing() {
        if (Modernizr.mq('screen and (min-width:768px)')) {
            // action for screen widths including and above 768 pixels

            if(uiHasCompact === true) {
                $ui.addClass('ui-aside-compact')
            }
            if(uiContentHasCompact === true) {
                $content.addClass('ui-content-compact')
            }

        } else if (Modernizr.mq('screen and (max-width:767px)')) {
            // action for screen widths below 768 pixels
            // console.log('Moblie');

            if(uiHasCompact === true) {
                $ui.removeClass('ui-aside-compact')
            }
            if(uiContentHasCompact === true) {
                $content.removeClass('ui-content-compact')
            }

            $uiDataNav.on('click', function(e){
                e.preventDefault();

                var hasAsideCompact = $ui.hasClass('ui-aside-compact'),
                    hasContentCompact = $content.hasClass('ui-content-compact');

                if(hasAsideCompact && hasContentCompact) {
                    $content.removeClass('ui-content-compact');
                }

            });

            $uiDataAsideRight.on('click', function(e){
                e.preventDefault();

                var hasAsideCompact = $ui.hasClass('ui-aside-compact'),
                    hasContentCompact = $content.hasClass('ui-content-compact');

                if(hasContentCompact && hasAsideCompact) {
                    $ui.removeClass('ui-aside-compact');
                }
            });
        }
    }

    var id;
    $window.on('resize', function () {
        clearTimeout(id);
        id = setTimeout(doneResizing, 0);
    });

    doneResizing();
    $('[data-toggle="tooltip"]').tooltip();
    $('[data-toggle="popover"]').popover();

    /**
     * Cache panel
     * @type {*|HTMLElement}
     */
    var $panel = $('.panel');

    /**
     * Collapse the panel once clicked on the collapse button
     */
    $panel.on('click.PanelCollapse', '.collapse-box', function(e) {
        var $this = $(this), // clicked element
            $thisPanel = $(e.delegateTarget),
            $thisPanelHead = $thisPanel.find('.panel-heading'),
            $thisPanelBody = $thisPanel.find('.panel-body');

        if ($this.hasClass("fa-chevron-down")) {
            $this.removeClass("fa-chevron-down").addClass("fa-chevron-up");
            $thisPanelHead.removeClass('panel-border');
            $thisPanelBody.slideUp(200);
        } else {
            $this.removeClass("fa-chevron-up").addClass("fa-chevron-down");
            $thisPanelHead.addClass('panel-border');
            $thisPanelBody.slideDown(200);
        }
    });

    /**
     * Delete the panel once clicked on the close button
     */
    $panel.on('click.PanelDestroy', '.close-box', function(e) {
        $(e.delegateTarget).remove();
    });

    /**
     * Refresh the panel once clicked on the refresh button
     */
    $panel.on('click.PanelRefresh', '.refresh-box', function(e) {
        e.preventDefault();

        var $thisPanel = $(e.delegateTarget),
            $refreshBlock = $("<div class='refresh-block'><span class='refresh-loader'><i class='fa fa-spinner fa-spin'></i></span></div>");

        $refreshBlock.appendTo($thisPanel);

        setTimeout(function() {
            $thisPanel.find('.refresh-block').remove();
        }, 1000);
    });

    /**
     * Resize all textarea with .autosize class
     */
    autosize($('.autosize'));

    /**
     * Initialize Nice Scroll with .niceScroll class
     */
    $(".niceScroll").niceScroll({
        zindex: "10",
        cursorwidth: "8px",
        cursoropacitymax: 0.3,
        cursorcolor: "#000000",
        cursordragontouch: true,
        cursorborderradius: "10px",
        cursorborder: "2px solid transparent"
    });
    var $mailboxNavAside = $('.Mailbox-nav-aside'),
        $mailboxList = $('.Mailbox-list'),
        $mailAnchor = $('.Mail > a'),
        $mailboxToggle = $('#toggle-mailbox-nav'),
        $mailboxReturn = $('#return-mailbox-list > a'),
        mailboxSideLeftClass = 'Mailbox-list--slideLeft';


    function bindMailAnchorEvents(listAction, asideAction) {
        var listAction = listAction || 'add',
            asideAction = asideAction || '';

        $mailAnchor.off('click.MailAnchor');
        $mailAnchor.on('click.MailAnchor', function(e) {
            e.preventDefault();

            if ('add' === listAction) {
                $mailboxList.addClass(mailboxSideLeftClass);
            } else if ('remove' === listAction) {
                $mailboxList.removeClass(mailboxSideLeftClass)
            }

            if ('show' === asideAction) {
                $mailboxNavAside.show();
            } else if ('hide' === asideAction) {
                $mailboxNavAside.hide();
            }
        });
    }

    $mailboxToggle.on('click.MailboxToggle', function(e) {
        e.stopPropagation();
        $mailboxNavAside.toggle();
    });

    $mailboxReturn.on('click.MailboxListReturn', function(e) {
        e.preventDefault();
        $mailboxList.removeClass(mailboxSideLeftClass);
    });

    if ($windowWidth <= 767) {
        bindMailAnchorEvents('add', 'hide');
    }

    if ($windowWidth <= 1200) {
        bindMailAnchorEvents('add');
    }

    $window.on('resize.Mailbox', function() {
        var $_windowWidth = $(this).width();

        if ($_windowWidth <= 767) {
            $mailboxNavAside.hide();
            bindMailAnchorEvents('add', 'hide');
        } else {
            $mailboxList.removeClass(mailboxSideLeftClass);
            $mailboxNavAside.show();
            bindMailAnchorEvents('remove', 'show');
        }

        if ($_windowWidth <= 1200) {
            bindMailAnchorEvents('add');
        } else {
            $mailboxList.removeClass(mailboxSideLeftClass);
            bindMailAnchorEvents('remove');
        }
    });
    var $noteList = $('.Note-list'),
        $noteAside = $('.Note-aside'),
        $returnNoteList = $('.return-note-list'),
        leftAsideClass = 'Note-aside--slideleft';

    /**
     * Bind click event to .Note item and add or remove class
     * based on given action param
     * @param action
     */
    function bindNoteListEvents(action) {
        var action = action || 'add';

        $noteList.off('click.NoteClick');

        $noteList.on('click.NoteClick', '.Note', function(e){
            e.preventDefault();

            if ('remove' === action) {
                $noteAside.removeClass(leftAsideClass);
            } else if ( 'add' === action ) {
                $noteAside.addClass(leftAsideClass);
            }
            console.log(action);

        });
    }

    $returnNoteList.on('click.NoteListReturn', function(e) {
        e.preventDefault();
        $noteAside.removeClass(leftAsideClass);
    });

    if ($windowWidth <= 767) {
        bindNoteListEvents();
    }

    $window.on('resize.NoteResize', function() {
        if ($(this).width() <= 767) {
            bindNoteListEvents();
        } else {
            $noteAside.removeClass(leftAsideClass);
            bindNoteListEvents('remove');
        }
    }); 
});
})(jQuery);