$('#sectionA').hover(
    // Mouse enters image
    function() {
        setTimeout(() => {
            $('#sectionA img').css({
                'transition': 'opacity 1s',
                'opacity': '0.5' // Example: change opacity to 50%
            });
        }, 100);
    },
    // Mouse leaves image
    function() {
        $('#sectionA img').css('opacity', '1'); // Change opacity back to 100%
    }
);
