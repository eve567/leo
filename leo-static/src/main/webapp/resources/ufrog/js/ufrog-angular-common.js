/*!
 *
 */
(function(ng, jq, undefined) {'use strict';
    /** 模块定义 */
    ng.module('ugCommon', [])

        /** 服务定义 */
        .service('$common', ['$window', '$filter', function($window, $filter) {
            var $_ = {
                /** jQuery */
                $: jq,

                /** 验证工具 */
                valid: {
                    /** 判断数据是否未定义 */
                    undefined: function(data) {
                        return ng.isUndefined(data);
                    },

                    /** 判断数据是否已定义 */
                    defined: function(data) {
                        return ng.isDefined(data);
                    },

                    /** 判断数据是否为对象 */
                    obj: function(data) {
                        return ng.isObject(data);
                    },

                    /** 判断数据是否为字符串 */
                    str: function(data) {
                        return ng.isString(data);
                    },

                    /** 判断数据是否为数字 */
                    num: function(data) {
                        return ng.isNumber(data);
                    },

                    /** 判断数据是否为日期 */
                    date: function(data) {
                        return ng.isDate(data);
                    },

                    /** 判断数据是否为数组 */
                    arr: function(data) {
                        return ng.isArray(data);
                    },

                    /** 判断数据是否为方法 */
                    fn: function(data) {
                        return ng.isFunction(data);
                    },

                    /** 判断数据是否为元素 */
                    elem: function(data) {
                        return ng.isElement(data);
                    },

                    /** 判断数据是否为空 */
                    empty: function(data) {
                        if ($_.valid.undefined(data) || data === null) return true;
                        else if ($_.valid.arr(data) && data.length === 0) return true;
                        else if ($_.valid.str(data) && data === '') return true;
                        return false;
                    },

                    /** 判断数据是否相同 */
                    equals: function(one, another, keys) {
                        if ($_.valid.obj(one) && $_.valid.obj(another)) {
                            var $bool = true;
                            ng.forEach($_.valid.arr(keys) ? keys : [keys], function(k) {
                                if (one[k] !== another[k]) $bool = false;
                            });
                            return $bool;
                        } else {
                            return one === another;
                        }
                    }
                },

                /** 数组工具 */
                array: {
                    /** 判断数据是否在数组中 */
                    in: function(array, data, key) {
                        var $idx = -1;
                        if ($_.valid.defined(data)) {
                            ng.forEach(array, function(val, idx) {
                                if ($_.valid.equals(data, val, key || 'id')) $idx = idx;
                            });
                        }
                        return $idx;
                    },

                    /** 将数据加入数组中 */
                    add: function(array, data, idx) {
                        array = array || [];
                        if ($_.valid.defined(data)) {
                            if ($_.valid.undefined(idx)) {
                                array.push(data);
                            } else {
                                array.splice(idx, 0, data);
                            }
                        }
                        return array;
                    },

                    /** 替换数组中数据 */
                    replace: function(array, data, key) {
                        array = array || [];
                        if ($_.valid.defined(data)) {
                            var $idx = $_.array.in(array, data, key);
                            if ($idx >= 0) {
                                array.splice($idx, 1, data);
                            } else {
                                $_.array.add(array, data);
                            }
                        }
                        return array;
                    },

                    /** 移除数组中数据 */
                    remove: function(array, data, key) {
                        array = array || [];
                        if ($_.valid.defined(data)) {
                            var $idx = $_.array.in(array, data, key);
                            if ($idx >= 0) {
                                array.splice($idx, 1);
                            }
                        }
                        return array;
                    },

                    /** 清空数组 */
                    empty: function(array) {
                        array.length = 0;
                    }
                },

                /** 对象工具 */
                object: {
                    /** 删除对象属性 */
                    delete: function(data, keys) {
                        if (!$_.valid.arr(keys)) keys = [keys];
                        ng.forEach(keys, function(key) {
                            delete(data[key]);
                        });
                        return data;
                    },

                    /** 解析成对象或转换成字符串 */
                    json: function(data) {
                        if ($_.valid.str(data)) {
                            return ng.fromJson(data);
                        } else {
                            return ng.toJSON(data);
                        }
                    }
                },

                /** 日期工具 */
                date: {
                    $mType: {
                        d: 24 * 60 * 60 * 1000,
                        data: this.d,
                        h: 60 * 60 * 1000,
                        hour: this.h,
                        m: 60 * 1000,
                        min: this.m,
                        minuter: this.m,
                        s: 1000,
                        second: this.s
                    },

                    /** 当前时间 */
                    current: function() {
                        return new Date();
                    },

                    /** 检查是否时间数据 */
                    check: function(date) {
                        return $_.valid.date(date) ? date : $_.date.current();
                    },

                    /** 生成时间戳 */
                    timestamp: function(date) {
                        return $_.date.check(date).valueOf();
                    },

                    /** 格式化时间 */
                    format: function(pattern, date) {
                        return $filter('date')($_.date.check(date), pattern || 'yyyy-MM-dd HH:mm:ss');
                    },

                    /** 时间累加 */
                    add: function(qty, type, date, pattern) {
                        var $unit = ($_.date.$mType[type] || $_.date.$mType.d),
                            $date = new Date($_.date.timestamp(date) + qty * $unit);
                        return $_.valid.defined(pattern) ? $_.date.format(pattern, $date) : $date;
                    }
                },

                /** 控制器工具 */
                ctrl: {
                    $mStatus: {},

                    /** 状态设定判断 */
                    status: function(name, status) {
                        if ($_.valid.arr(status)) {
                            return $_.array.in(status, $_.ctrl.$mStatus[name]) >= 0;
                        } else {
                            $_.ctrl.$mStatus[name] = status;
                        }
                    },

                    /** 阻止事件冒泡 */
                    stop: function(event) {
                        event.preventDefault();
                        event.stopPropagation();
                    },

                    /** 页面跳转 */
                    redirect: function(url) {
                        $window.location.href = url;
                    },

                    /** 重置表单 */
                    resetForm: function(form) {
                        ng.forEach(form, function(val, key) {
                            if (!key.startsWith('$')) {
                                val.$dirty = false;
                            }
                        });
                    },

                    /** 表单数据 */
                    formData: function(data) {
                        var $data = new FormData();
                        ng.forEach(data, function(val, idx) {
                            if ($_.valid.arr(val)) {
                                ng.forEach(val, function(v) {
                                    $data.append(idx, v);
                                });
                            } else {
                                $data.append(idx, val);
                            }
                        });
                        return $data;
                    },

                    /** 脚本路径 */
                    scriptPath: function(names) {
                        var $selector = [], $tags;
                        ng.forEach($_.valid.arr(names) ? names : [names], function(n, i) {
                            $selector[i] = 'script[src*="' + n + '"]';
                        });
                        $tags = $_.$($selector.join(','));
                        return ($tags.length > 0) ? $tags.get(0).src.substr(0, $tags.get(0).src.lastIndexOf('/')) : undefined;
                    }
                },

                /** 编码器工具 */
                codec: {
                    /** 转换成统一编码 */
                    toUnicode: function(str) {
                        if (!$_.valid.empty(str) && $_.valid.str(str)) {
                            var $unicode = '';
                            for (var $i = 0; $i < str.length; $i++) {
                                $unicode += ('\\u' + ('0000' + str.charCodeAt($i).toString(16)).slice(-4));
                            }
                            return $unicode;
                        }
                        return str;
                    },

                    /** 从统一编码转换 */
                    fromUnicode: function(unicode) {
                        if (!$_.valid.empty(unicode) && $_.valid.str(unicode)) {
                            var $str = '', $data = unicode.split('\\u');
                            for (var $i = 0; $i < $data.length; $i++) {
                                $str += String.fromCharCode(parseInt(parseInt($data[$i], 16).toString(10)));
                            }
                            return $str;
                        }
                        return unicode;
                    },

                    /** 散列字符串 */
                    md5: function(str) {
                        var $aa, $bb, $cc, $dd,
                            $a = 0x67452301, $b = 0xEFCDAB89, $c = 0x98BADCFE, $d = 0x10325476,
                            $S11 = 7, $S12 = 12, $S13 = 17, $S14 = 22,
                            $S21 = 5, $S22 = 9,  $S23 = 14, $S24 = 20,
                            $S31 = 4, $S32 = 11, $S33 = 16, $S34 = 23,
                            $S41 = 6, $S42 = 10, $S43 = 15, $S44 = 21,
                            $x = $_.codec.$fn.convertToWordArray($_.codec.$fn.utf8Encode(str));

                        for (var $k = 0; $k < $x.length; $k += 16) {
                            $aa = $a;
                            $bb = $b;
                            $cc = $c;
                            $dd = $d;

                            $a = $_.codec.$fn.FF($a, $b, $c, $d, $x[$k + 0],  $S11, 0xD76AA478);
                            $d = $_.codec.$fn.FF($d, $a, $b, $c, $x[$k + 1],  $S12, 0xE8C7B756);
                            $c = $_.codec.$fn.FF($c, $d, $a, $b, $x[$k + 2],  $S13, 0x242070DB);
                            $b = $_.codec.$fn.FF($b, $c, $d, $a, $x[$k + 3],  $S14, 0xC1BDCEEE);
                            $a = $_.codec.$fn.FF($a, $b, $c, $d, $x[$k + 4],  $S11, 0xF57C0FAF);
                            $d = $_.codec.$fn.FF($d, $a, $b, $c, $x[$k + 5],  $S12, 0x4787C62A);
                            $c = $_.codec.$fn.FF($c, $d, $a, $b, $x[$k + 6],  $S13, 0xA8304613);
                            $b = $_.codec.$fn.FF($b, $c, $d, $a, $x[$k + 7],  $S14, 0xFD469501);
                            $a = $_.codec.$fn.FF($a, $b, $c, $d, $x[$k + 8],  $S11, 0x698098D8);
                            $d = $_.codec.$fn.FF($d, $a, $b, $c, $x[$k + 9],  $S12, 0x8B44F7AF);
                            $c = $_.codec.$fn.FF($c, $d, $a, $b, $x[$k + 10], $S13, 0xFFFF5BB1);
                            $b = $_.codec.$fn.FF($b, $c, $d, $a, $x[$k + 11], $S14, 0x895CD7BE);
                            $a = $_.codec.$fn.FF($a, $b, $c, $d, $x[$k + 12], $S11, 0x6B901122);
                            $d = $_.codec.$fn.FF($d, $a, $b, $c, $x[$k + 13], $S12, 0xFD987193);
                            $c = $_.codec.$fn.FF($c, $d, $a, $b, $x[$k + 14], $S13, 0xA679438E);
                            $b = $_.codec.$fn.FF($b, $c, $d, $a, $x[$k + 15], $S14, 0x49B40821);
                            $a = $_.codec.$fn.GG($a, $b, $c, $d, $x[$k + 1],  $S21, 0xF61E2562);
                            $d = $_.codec.$fn.GG($d, $a, $b, $c, $x[$k + 6],  $S22, 0xC040B340);
                            $c = $_.codec.$fn.GG($c, $d, $a, $b, $x[$k + 11], $S23, 0x265E5A51);
                            $b = $_.codec.$fn.GG($b, $c, $d, $a, $x[$k + 0],  $S24, 0xE9B6C7AA);
                            $a = $_.codec.$fn.GG($a, $b, $c, $d, $x[$k + 5],  $S21, 0xD62F105D);
                            $d = $_.codec.$fn.GG($d, $a, $b, $c, $x[$k + 10], $S22, 0x2441453);
                            $c = $_.codec.$fn.GG($c, $d, $a, $b, $x[$k + 15], $S23, 0xD8A1E681);
                            $b = $_.codec.$fn.GG($b, $c, $d, $a, $x[$k + 4],  $S24, 0xE7D3FBC8);
                            $a = $_.codec.$fn.GG($a, $b, $c, $d, $x[$k + 9],  $S21, 0x21E1CDE6);
                            $d = $_.codec.$fn.GG($d, $a, $b, $c, $x[$k + 14], $S22, 0xC33707D6);
                            $c = $_.codec.$fn.GG($c, $d, $a, $b, $x[$k + 3],  $S23, 0xF4D50D87);
                            $b = $_.codec.$fn.GG($b, $c, $d, $a, $x[$k + 8],  $S24, 0x455A14ED);
                            $a = $_.codec.$fn.GG($a, $b, $c, $d, $x[$k + 13], $S21, 0xA9E3E905);
                            $d = $_.codec.$fn.GG($d, $a, $b, $c, $x[$k + 2],  $S22, 0xFCEFA3F8);
                            $c = $_.codec.$fn.GG($c, $d, $a, $b, $x[$k + 7],  $S23, 0x676F02D9);
                            $b = $_.codec.$fn.GG($b, $c, $d, $a, $x[$k + 12], $S24, 0x8D2A4C8A);
                            $a = $_.codec.$fn.HH($a, $b, $c, $d, $x[$k + 5],  $S31, 0xFFFA3942);
                            $d = $_.codec.$fn.HH($d, $a, $b, $c, $x[$k + 8],  $S32, 0x8771F681);
                            $c = $_.codec.$fn.HH($c, $d, $a, $b, $x[$k + 11], $S33, 0x6D9D6122);
                            $b = $_.codec.$fn.HH($b, $c, $d, $a, $x[$k + 14], $S34, 0xFDE5380C);
                            $a = $_.codec.$fn.HH($a, $b, $c, $d, $x[$k + 1],  $S31, 0xA4BEEA44);
                            $d = $_.codec.$fn.HH($d, $a, $b, $c, $x[$k + 4],  $S32, 0x4BDECFA9);
                            $c = $_.codec.$fn.HH($c, $d, $a, $b, $x[$k + 7],  $S33, 0xF6BB4B60);
                            $b = $_.codec.$fn.HH($b, $c, $d, $a, $x[$k + 10], $S34, 0xBEBFBC70);
                            $a = $_.codec.$fn.HH($a, $b, $c, $d, $x[$k + 13], $S31, 0x289B7EC6);
                            $d = $_.codec.$fn.HH($d, $a, $b, $c, $x[$k + 0],  $S32, 0xEAA127FA);
                            $c = $_.codec.$fn.HH($c, $d, $a, $b, $x[$k + 3],  $S33, 0xD4EF3085);
                            $b = $_.codec.$fn.HH($b, $c, $d, $a, $x[$k + 6],  $S34, 0x4881D05);
                            $a = $_.codec.$fn.HH($a, $b, $c, $d, $x[$k + 9],  $S31, 0xD9D4D039);
                            $d = $_.codec.$fn.HH($d, $a, $b, $c, $x[$k + 12], $S32, 0xE6DB99E5);
                            $c = $_.codec.$fn.HH($c, $d, $a, $b, $x[$k + 15], $S33, 0x1FA27CF8);
                            $b = $_.codec.$fn.HH($b, $c, $d, $a, $x[$k + 2],  $S34, 0xC4AC5665);
                            $a = $_.codec.$fn.II($a, $b, $c, $d, $x[$k + 0],  $S41, 0xF4292244);
                            $d = $_.codec.$fn.II($d, $a, $b, $c, $x[$k + 7],  $S42, 0x432AFF97);
                            $c = $_.codec.$fn.II($c, $d, $a, $b, $x[$k + 14], $S43, 0xAB9423A7);
                            $b = $_.codec.$fn.II($b, $c, $d, $a, $x[$k + 5],  $S44, 0xFC93A039);
                            $a = $_.codec.$fn.II($a, $b, $c, $d, $x[$k + 12], $S41, 0x655B59C3);
                            $d = $_.codec.$fn.II($d, $a, $b, $c, $x[$k + 3],  $S42, 0x8F0CCC92);
                            $c = $_.codec.$fn.II($c, $d, $a, $b, $x[$k + 10], $S43, 0xFFEFF47D);
                            $b = $_.codec.$fn.II($b, $c, $d, $a, $x[$k + 1],  $S44, 0x85845DD1);
                            $a = $_.codec.$fn.II($a, $b, $c, $d, $x[$k + 8],  $S41, 0x6FA87E4F);
                            $d = $_.codec.$fn.II($d, $a, $b, $c, $x[$k + 15], $S42, 0xFE2CE6E0);
                            $c = $_.codec.$fn.II($c, $d, $a, $b, $x[$k + 6],  $S43, 0xA3014314);
                            $b = $_.codec.$fn.II($b, $c, $d, $a, $x[$k + 13], $S44, 0x4E0811A1);
                            $a = $_.codec.$fn.II($a, $b, $c, $d, $x[$k + 4],  $S41, 0xF7537E82);
                            $d = $_.codec.$fn.II($d, $a, $b, $c, $x[$k + 11], $S42, 0xBD3AF235);
                            $c = $_.codec.$fn.II($c, $d, $a, $b, $x[$k + 2],  $S43, 0x2AD7D2BB);
                            $b = $_.codec.$fn.II($b, $c, $d, $a, $x[$k + 9],  $S44, 0xEB86D391);

                            $a = $_.codec.$fn.addUnsigned($a, $aa);
                            $b = $_.codec.$fn.addUnsigned($b, $bb);
                            $c = $_.codec.$fn.addUnsigned($c, $cc);
                            $d = $_.codec.$fn.addUnsigned($d, $dd);
                        }
                        return ($_.codec.$fn.wordToHex($a) + $_.codec.$fn.wordToHex($b) + $_.codec.$fn.wordToHex($c) + $_.codec.$fn.wordToHex($d)).toLowerCase();
                    },

                    /** 中间方法集合 */
                    $fn: {
                        rotateLeft: function(value, shiftBits) {
                            return (value << shiftBits) | (value >>> (32 - shiftBits));
                        },

                        addUnsigned: function(x, y) {
                            var $x4, $y4, $x8, $y8, $result;
                            $x8 = (x & 0x80000000);
                            $y8 = (y & 0x80000000);
                            $x4 = (x & 0x40000000);
                            $y4 = (y & 0x40000000);
                            $result = (x & 0x3FFFFFFF) + (y & 0x3FFFFFFF);

                            if ($x4 & $y4) {
                                return ($result ^ 0x80000000 ^ $x8 ^ $y8);
                            } else if ($x4 | $y4) {
                                if ($result & 0x40000000) {
                                    return ($result ^ 0xC0000000 ^ $x8 ^ $y8);
                                } else {
                                    return ($result ^ 0x40000000 ^ $x8 ^ $y8);
                                }
                            } else {
                                return ($result ^ $x8 ^ $y8);
                            }
                        },

                        F: function(x, y, z) {
                            return (x & y) | ((~ x) & z);
                        },

                        G: function(x, y, z) {
                            return (x & z) | (y & (~ z));
                        },

                        H: function(x, y, z) {
                            return (x ^ y ^ z);
                        },

                        I: function(x, y, z) {
                            return (y ^ (x | (~ z)));
                        },

                        FF: function(a, b, c, d, x, s, ac) {
                            a = $_.codec.$fn.addUnsigned(a, $_.codec.$fn.addUnsigned($_.codec.$fn.addUnsigned($_.codec.$fn.F(b, c, d), x), ac));
                            return $_.codec.$fn.addUnsigned($_.codec.$fn.rotateLeft(a, s), b);
                        },

                        GG: function(a, b, c, d, x, s, ac) {
                            a = $_.codec.$fn.addUnsigned(a, $_.codec.$fn.addUnsigned($_.codec.$fn.addUnsigned($_.codec.$fn.G(b, c, d), x), ac));
                            return $_.codec.$fn.addUnsigned($_.codec.$fn.rotateLeft(a, s), b);
                        },

                        HH: function(a, b, c, d, x, s, ac) {
                            a = $_.codec.$fn.addUnsigned(a, $_.codec.$fn.addUnsigned($_.codec.$fn.addUnsigned($_.codec.$fn.H(b, c, d), x), ac));
                            return $_.codec.$fn.addUnsigned($_.codec.$fn.rotateLeft(a, s), b);
                        },

                        II: function(a, b, c, d, x, s, ac) {
                            a = $_.codec.$fn.addUnsigned(a, $_.codec.$fn.addUnsigned($_.codec.$fn.addUnsigned($_.codec.$fn.I(b, c, d), x), ac));
                            return $_.codec.$fn.addUnsigned($_.codec.$fn.rotateLeft(a, s), b);
                        },

                        convertToWordArray: function(str) {
                            var $wordCount,
                                $messageLen = str.length,
                                $tempOne = $messageLen + 8,
                                $tempTwo = ($tempOne - ($tempOne % 64)) / 64,
                                $wordSize = ($tempTwo + 1) * 16,
                                $wordArray = [],
                                $bytePos = 0,
                                $byteCount = 0;

                            while ($byteCount < $messageLen) {
                                $wordCount = ($byteCount - ($byteCount % 4)) / 4;
                                $bytePos = ($byteCount % 4) * 8;
                                $wordArray[$wordCount] = ($wordArray[$wordCount] | (str.charCodeAt($byteCount) << $bytePos));
                                $byteCount++;
                            }
                            $wordCount = ($byteCount - ($byteCount % 4)) / 4;
                            $bytePos = ($byteCount % 4) * 8;
                            $wordArray[$wordCount] = $wordArray[$wordCount] | (0x80 << $bytePos);
                            $wordArray[$wordSize - 2] = $messageLen << 3;
                            $wordArray[$wordSize - 1] = $messageLen >>> 29;
                            return $wordArray;
                        },

                        wordToHex: function(value) {
                            var $hex = '';
                            for (var $count = 0; $count <= 3; $count++) {
                                var $byte = (value >>> ($count * 8)) & 255;
                                var $temp = '00' + $byte.toString(16);
                                $hex += $temp.slice(-2);
                            }
                            return $hex;
                        },

                        utf8Encode: function(str) {
                            str = str.replace(/\x0d\x0a/g, '\x0a');
                            var $output = '';
                            for (var $i = 0; $i < str.length; $i++) {
                                var $char = str.charCodeAt($i);
                                if ($char < 128) {
                                    $output += String.fromCharCode($char);
                                } else if (($char >= 128) && ($char < 2048)) {
                                    $output += String.fromCharCode(($char >> 6) | 192);
                                    $output += String.fromCharCode(($char & 63) | 128);
                                } else {
                                    $output += String.fromCharCode(($char >> 12) | 224);
                                    $output += String.fromCharCode((($char >> 6) & 63) | 128);
                                    $output += String.fromCharCode(($char & 63) | 128);
                                }
                            }
                            return $output;
                        }
                    }
                }
            };
            return $_;
        }])

        /** 图标指令 */
        .directive('fa', function() {
            return {
                restrict: 'E',
                replace: true,
                scope: true,
                transclude: true,
                template: '<span><i class="fa" ng-class="$class()"></i><ng-transclude></ng-transclude></span>',
                link: function($scope, $element, $attrs) {
                    ng.extend($scope, {
                        $class: function() {
                            return 'fa-' + $attrs['i'].split(' ').join(' fa-');
                        }
                    });
                }
            };
        })

        /** 空数据过滤器 */
        .filter('empty', ['$common', function($common) {
            return function(data, defaultValue) {
                return $common.valid.empty(data) ? defaultValue : data;
            };
        }])

        /** 排除数据过滤器 */
        .filter('exclude', ['$common', function($common) {
            return function(data, exclude, key) {
                if ($common.valid.arr(data)) {
                    data = data.concat();
                    ng.forEach($common.valid.arr(exclude) ? exclude : [exclude], function(ex) {
                        $common.array.remove(data, ex, key);
                    });
                }
                return data;
            }
        }]);
})(window.angular, window.jQuery);