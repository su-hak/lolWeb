$('#sectionA').hover(
    // Mouse enters image
    function() {
        setTimeout(() => {
            $('.lol_main_img img').css({
                'transition': 'opacity 1s',
                'opacity': '0.5' // Example: change opacity to 50%
            });
        }, 100);
    },
    // Mouse leaves image
    function() {
        $('.lol_main_img img').css('opacity', '0.7'); // Change opacity back to 100%
    }
);
