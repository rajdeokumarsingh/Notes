define find-prog
    $(wildcard  \
        $(addsuffix /$1,    \
            $(sort     \
                $(subst :, ,    \
                    $(subst ::,:.:,    \
                        $(patsubst :%,.:%,    \
                            $(patsubst %:,%:.,$(PATH))    \
                        )    \
                    )    \
                )    \
            )    \
        )    \
    )
endef

$(info find gvim, $(call find-prog,gvim))
