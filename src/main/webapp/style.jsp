/* PC통신 전용 레트로 픽셀 폰트 (네오둥근모) 적용 */
        @import url('https://cdn.jsdelivr.net/gh/neodgm/neodgm-webfont@1.530/neodgm/style.css');
        
        * { box-sizing: border-box; margin: 0; padding: 0; }
        
        /* 쨍한 파란색 배경과 기본 흰색 글씨 */
        body { 
            font-family: 'NeoDunggeunmo', '돋움', Dotum, monospace; 
            background-color: #0000AA; /* PC통신 특유의 진파랑 */
            color: #FFFFFF; 
            line-height: 1.5;
        }

        /* 텍스트 선택 시 색상 반전 */
        ::selection { background: #FFFFFF; color: #0000AA; }

        /* 전체 레이아웃 (선명한 흰색 실선 테두리) */
        .container { 
            display: flex; 
            max-width: 1050px; 
            margin: 40px auto; 
            background: #0000AA; 
            border: 2px solid #FFFFFF; 
            min-height: 800px; 
        }

        /* 사이드바 영역 */
        .sidebar { 
            width: 260px; 
            padding: 30px 20px; 
            border-right: 2px solid #FFFFFF; 
            display: flex; 
            flex-direction: column; 
            align-items: center; 
        }
        
        .profile-section { text-align: center; margin-bottom: 40px; width: 100%; border-bottom: 1px solid #FFFFFF; padding-bottom: 30px; }
        .profile-img { 
            width: 90px; height: 90px; 
            border: 2px solid #FFFF00; /* 노란색 포인트 */
            margin: 0 auto 15px; 
            display: flex; align-items: center; justify-content: center; 
            font-size: 40px; 
            background: #0000AA;
            color: #FFFF00;
        }
        .profile-name { font-weight: normal; font-size: 1.3rem; color: #FFFF00; }

        /* 공통 버튼 디자인 */
        .btn-primary {
            width: 100%; padding: 8px; margin-top: 15px;
            background-color: #0000AA; color: #FFFFFF;
            border: 2px solid #FFFFFF; 
            font-family: 'NeoDunggeunmo', monospace;
            font-size: 1.1rem; cursor: pointer;
        }
        .btn-primary:hover { background-color: #00FFFF; color: #0000AA; border-color: #00FFFF; }

        /* 입력 폼 디자인 */
        .input-box {
            width: 100%; padding: 10px; margin-bottom: 10px;
            border: 2px solid #FFFFFF; 
            background-color: #0000AA; color: #FFFFFF;
            font-family: 'NeoDunggeunmo', monospace;
            font-size: 1.1rem;
        }
        .input-box::placeholder { color: #AAAAAA; }
        .input-box:focus { outline: none; border-color: #FFFF00; background-color: #000055; }

        /* 카테고리 메뉴 */
        .category-menu { width: 100%; }
        .category-menu h3 { 
            font-size: 1.2rem; color: #FFFF00; margin-bottom: 15px; 
            text-align: center;
        }
        .category-menu ul { list-style: none; border: 1px solid #FFFFFF; padding: 10px; }
        .category-menu li { 
            padding: 8px 10px; font-size: 1.2rem; cursor: pointer; color: #FFFFFF; 
            display: flex; gap: 10px;
        }
        .category-menu li .num { color: #00FFFF; } /* 번호는 청록색 */
        .category-menu li:hover { background-color: #FFFFFF; color: #0000AA; }

        /* 메인 콘텐츠 영역 */
        .main-content { flex: 1; padding: 40px; display: flex; flex-direction: column; }
        
        .header-title { 
            font-size: 2.5rem; font-weight: normal; margin-bottom: 40px; 
            color: #FFFFFF; text-align: center;
            text-shadow: 2px 2px #000000;
        }
        .header-title span { color: #FFFF00; }

        .section-title { 
            font-size: 1.4rem; margin-bottom: 20px; 
            color: #FFFF00; background-color: #0000AA;
            display: inline-block; padding: 0 10px;
        }
        
        .post-container { flex: 1; margin-bottom: 50px; border-top: 2px solid #FFFFFF; padding-top: 20px; }
        
        /* 게시글 리스트 디자인 (표 형태 느낌) */
        .post-card { 
            border-bottom: 1px dashed #FFFFFF; 
            padding: 15px 0; 
            margin-bottom: 10px; 
            display: flex; flex-direction: column; gap: 10px;
        }
        .post-card:hover { background-color: #000055; }
        .post-title { font-size: 1.3rem; font-weight: normal; color: #FFFFFF; }
        .post-title::before { content: '\25B6'; color: #00FFFF; }
        .post-body { color: #CCCCCC; font-size: 1.1rem; padding-left: 25px; }
        .post-reaction { text-align: right; color: #00FFFF; font-size: 1.1rem; }
        
        .post-card.empty { 
            align-items: center; justify-content: center; 
            height: 100px; text-align: center; border: none;
        }

        /* 오늘의 노래 섹션 */
        .song-section { border: 2px solid #FFFFFF; padding: 25px; position: relative; }
        .song-section .section-title { position: absolute; top: -15px; left: 20px; }
        
        .song-input-area { display: flex; gap: 10px; margin-top: 15px; }
        .song-input-area input { flex: 1; margin-bottom: 0; }
        
        .video-area { 
            display: none; margin-top: 20px; width: 100%; aspect-ratio: 16 / 9; 
            background-color: #000000; border: 2px solid #FFFFFF; 
        }
        .video-area iframe { width: 100%; height: 100%; border: none; }
        
        /* 깜빡이는 프롬프트 커서 */
        .blink { animation: blinker 1s step-end infinite; }
        @keyframes blinker { 50% { opacity: 0; } }
        
        .category-btn {
    	background-color: transparent; /* 배경 투명하게 */
    	border: none;                  /* 테두리 없애기 */
    	color: inherit;                /* 부모(li)의 글자색 따라가기 */
    	font-family: inherit;          /* 폰트 모양 따라가기 */
    	font-size: inherit;            /* 폰트 크기 따라가기 */
    	cursor: pointer;               /* 마우스 올리면 손가락 모양 */
    	width: 100%;                   /* 클릭 영역 꽉 채우기 */
    	text-align: left;
    	display: flex;
    	gap: 10px;
    	padding: 0;
	}
		.category-btn:focus { 
    		outline: none; /* 클릭했을 때 띠 생기는 것 방지 */
	}
	
	
	