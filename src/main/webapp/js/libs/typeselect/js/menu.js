;(function(a) {
    a.fn.hoverDelay = function(c, f, g, b) {
        var g = g || 200,
        b = b || 200,
        f = f || c;
        var e = [],
        d = [];
        return this.each(function(h) {
            a(this).mouseenter(function() {
                var i = this;
                clearTimeout(d[h]);
                e[h] = setTimeout(function() {
                    c.apply(i)
                },
                g)
            }).mouseleave(function() {
                var i = this;
                clearTimeout(e[h]);
                d[h] = setTimeout(function() {
                    f.apply(i)
                },
                b)
            })
        })
    }
})(jQuery);

$(function(){
	
	$(".sidebar").hoverDelay(function(){
        $("#sidebar_box").show();
        $(".sidebar_top s").addClass("s_down");
    },
    function() {
        $("#sidebar_box").hide();
        $(".sidebar_top s").removeClass("s_down");
    });
	
    $(".sidebar_item dd").hoverDelay(function() {
        $(this).find("h3").addClass("sidebar_focus");
        $(this).find(".sidebar_popup").show(0);
    },
    function(){
        $(this).find("h3").removeClass("sidebar_focus");
        $(this).find(".sidebar_popup").hide(0);
    });
	
});