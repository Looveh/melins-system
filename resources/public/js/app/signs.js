var signs = (function() {
    var coords = {};
    coords["a"] = {start: {x: 11, y: 32}, end: {x: 49, y: 18}};
    coords["b"] = {start: {x: 27, y: 6}, end: {x: 8, y: 40}};
    coords["c"] = {
        start: {
            x: 18,
            y: 7
        },
        end: {
            x: 21,
            y: 43
        }
    };
    coords["d"] = {
        start: {
            x: 9,
            y: 12
        },
        end: {
            x: 7,
            y: 40
        }
    };
    coords["e"] = {
        start: {
            x: 10,
            y: 40
        },
        end: {
            x: 30,
            y: 7
        }
    };
    coords["f"] = {
        start: {
            x: 43,
            y: 7
        },
        end: {
            x: 24,
            y: 35
        }
    };
    coords["g"] = {
        start: {
            x: 18,
            y: 29
        },
        end: {
            x: 25,
            y: 41
        }
    };
    coords["h"] = {
        start: {
            x: 47,
            y: 8
        },
        end: {
            x: 33,
            y: 28
        }
    };
    coords["i"] = {
        start: {
            x: 6,
            y: 34
        },
        end: {
            x: 135,
            y: 7
        }
    };
    coords["j"] = {
        start: {
            x: 36,
            y: 17
        },
        end: {
            x: 40,
            y: 30
        }
    };
    coords["k"] = {
        start: {
            x: 18,
            y: 27
        },
        end: {
            x: 9,
            y: 44
        }
    };
    coords["l"] = {
        start: {
            x: 16,
            y: 28
        },
        end: {
            x: 23,
            y: 30
        }
    };
    coords["m"] = {
        start: {
            x: 10,
            y: 11
        },
        end: {
            x: 29,
            y: 32
        }
    };
    coords["n"] = {
        start: {
            x: 13,
            y: 10
        },
        end: {
            x: 20,
            y: 13
        }
    };
    coords["o"] = {
        start: {
            x: 6,
            y: 9
        },
        end: {
            x: 44,
            y: 10
        }
    };
    coords["p"] = {
        start: {
            x: 49,
            y: 6
        },
        end: {
            x: 7,
            y: 41
        }
    };
    coords["q"] = {
        start: {
            x: 15,
            y: 30
        },
        end: {
            x: 12,
            y: 43
        }
    };
    coords["r"] = {
        start: {
            x: 19,
            y: 15
        },
        end: {
            x: 27,
            y: 15
        }
    };
    coords["s"] = {
        start: {
            x: 21,
            y: 12
        },
        end: {
            x: 26,
            y: 13
        }
    };
    coords["t"] = {
        start: {
            x: 6,
            y: 11
        },
        end: {
            x: 24,
            y: 25
        }
    };
    coords["u"] = {
        start: {
            x: 6,
            y: 17
        },
        end: {
            x: 57,
            y: 7
        }
    };
    coords["v"] = {
        start: {
            x: 27,
            y: 7
        },
        end: {
            x: 26,
            y: 37
        }
    };
    coords["w"] = {
        start: {
            x: 27,
            y: 7
        },
        end: {
            x: 26,
            y: 37
        }
    };
    coords["y"] = {
        start: {
            x: 9,
            y: 17
        },
        end: {
            x: 172,
            y: 6
        }
    };
    coords["z"] = {
        start: {
            x: 36,
            y: 12
        },
        end: {
            x: 59,
            y: 32
        }
    };
    coords[String.fromCharCode(229)] = {
        start: {
            x: 6,
            y: 6
        },
        end: {
            x: 169,
            y: 11
        }
    }; // å
    coords[String.fromCharCode(228)] = {
        start: {
            x: 7,
            y: 72
        },
        end: {
            x: 47,
            y: 4
        }
    }; // ä
    coords[String.fromCharCode(246)] = {
        start: {
            x: 7,
            y: 41
        },
        end: {
            x: 80,
            y: 6
        }
    }; // ö

    return {coords: coords};
})();