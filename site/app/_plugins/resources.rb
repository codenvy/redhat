module Jekyll
  module Resources
    def resource(input)
        case @context.registers[:site].config['profile']
        when "yeoman"
            "/_site" + input
        when "gh"
            # use relative paths for github pages
            #input.sub(/^\//,"")
            input
        else
            input
        end
    end

    def script(input)
        case @context.registers[:site].config['profile']
        when "stage","gh","enterprise", "onpremSE"
            "/" + input
        else
            input
        end
    end
  end
end

Liquid::Template.register_filter(Jekyll::Resources)
