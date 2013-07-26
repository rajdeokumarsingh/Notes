obj = foo.c bar.c x.cpp y.l a.o b.p d.o .o

# % 等价于 .*
$(info test filter...)
$(info origin, $(obj))
$(info filter .o, $(filter %.o,$(obj)))
$(info filter .o .c, $(filter %.o %c,$(obj)))
$(info )

# filter 是全字符串匹配的， 如/usr/bin/vim, %vim可匹配，但是vim却不能匹配
# XXX 文本中仅仅只能包括一个%， 其他的%被看成是文本符号 literal character

$(info test filter-out...)
$(info origin, $(obj))
$(info filter-out .o, $(filter-out %.o,$(obj)))
$(info filter-out .o .l, $(filter-out %.o %.l,$(obj)))
$(info )

$(info test findstring...)
$(info findstring foo, $(findstring foo,$(obj))) # foo.c
$(info findstring bar, $(findstring bar,$(obj))) # bar.c
$(info findstring car, $(findstring car,$(obj))) # NON
$(info )

$(info test subst...)
$(info origin string, $(obj))
$(info subst .c to .o,$(subst .c,.o,$(obj)))#注意.c,.o,$中间不要加空格
$(info )

$(info test patsubst...)
$(info origin string, $(obj))
$(info patsubst .c to .o,$(patsubst %.c,%.o,$(obj)))#注意.c,.o,$中间不要加空格
$(info )

$(info test patsubst 2...)
$(info origin string, $(obj))
$(info patsubst2 .c to .o,$(obj:%.c=%.o))
$(info )

$(info test words...)
$(info origin string, $(obj))
$(info word count, $(words $(obj)))
$(info )

$(info test nth word...)
$(info origin string, $(obj))
$(info word 3th, $(word 3,$(obj)))
$(info )

$(info first word...)
$(info origin string, $(obj))
$(info first word, $(firstword $(obj)))
$(info )

$(info word list...)
$(info origin string, $(obj))
$(info word 2 to 4, $(wordlist 2,4,$(obj)))
$(info )

trail := ending       #
$(info ending space, "$(trail)")
$(info strip, "$(strip $(trail))")
$(info )

$(info test origin)
$(info undef var, $(origin nnn))
$(info env var, $(origin HOME))
$(info cmdline var, $(origin STOPCOPY))
$(info makefile default, $(origin MAKE_VERSION))
$(info makefile var, $(origin trail))
$(info )









