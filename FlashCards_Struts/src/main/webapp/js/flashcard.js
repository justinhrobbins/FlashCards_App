
var FlashCardLink = function () {
    var linkLabel;
    var linkCount;

    function getNamePrefix() {          
        return "flashCard.links[" + linkCount + "]";
    };
    
    function createLinkLabel() {
        return linkLabel + ' #' + (linkCount + 1) + ':';
    };
    
    function createInputTag(name, size, id) {
        var s = '<input type="text" size="' + size + '" name="' + name + '"';
        if (id) { s += ' id="' + id + '"'; }
        return s + '/>';
    }
    
    function createNameInput() {
        return createInputTag(getNamePrefix(), 66);
    };
    
    function createIngredientRow() {
        return [
            '<tr>',
            '  <td class="tdLabel">',
            '    <label class="label">',
            '      ' + createLinkLabel(), 
            '    </label>',
            '  </td>',
            '  <td>',
            '    ' + createNameInput(),
            '  </td>',
            '</tr>'
        ].join("\n");
    }
    
    return {
    	prepare: function (label, count) {
    		linkLabel = label;
    		linkCount = count;
    	},
    	
        addRow: function (label) {
            $("#addRow").before(createIngredientRow());
            $("#count_" + linkCount).focus();
            linkCount++;
            return false;
        }
    };
}();    
